package com.laicos.khufarm.domain.auth.service;

import com.laicos.khufarm.domain.auth.dto.AccessTokenResponse;
import com.laicos.khufarm.domain.auth.dto.IDFindRequest;
import com.laicos.khufarm.domain.auth.dto.PasswordChangeRequest;
import com.laicos.khufarm.domain.auth.dto.PasswordFindRequest;
import com.laicos.khufarm.domain.auth.entity.RefreshToken;
import com.laicos.khufarm.domain.auth.jwt.JwtTokenProvider;
import com.laicos.khufarm.domain.auth.repository.RefreshTokenRepository;
import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.user.repository.UserRepository;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.AuthErrorStatus;
import com.laicos.khufarm.global.common.exception.code.status.UserErrorStatus;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthCommandServiceImpl implements AuthCommandService{

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AccessTokenResponse reissueToken(String oldRefreshToken, HttpServletResponse response) {
        log.info("Client refresh token: {}", oldRefreshToken);
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByToken(oldRefreshToken)
                .orElseThrow(() -> {
                    log.warn("DB에 저장된 refresh token과 일치하는 값이 없습니다.");
                    return new RestApiException(AuthErrorStatus.INVALID_REFRESH_TOKEN);
                });
        log.info("DB refresh token for member {}: {}", refreshTokenEntity.getUser().getId(),
                refreshTokenEntity.getToken());

        // Refresh Token에 해당하는 회원 조회
        User user = refreshTokenEntity.getUser();

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                customUserDetails,
                null,
                List.of(new SimpleGrantedAuthority(user.getUserType().toString()))
        );

        // 새로운 토큰 발급
        String newAccessToken = jwtTokenProvider.generateToken(authentication).getAccessToken();
        String newRefreshToken = jwtTokenProvider.generateToken(authentication).getRefreshToken();

        // Refresh Token 교체 (Rotation)
        refreshTokenEntity.updateToken(newRefreshToken);

        // Refresh Token을 쿠키에 설정
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", newRefreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(7 * 24 * 60 * 60) // 7일
                .path("/")
                .build();

        // Access Token을 Authorization 헤더에 설정

        response.setHeader("Set-Cookie", refreshTokenCookie.toString());

        return AccessTokenResponse.builder()
                .accessToken(newAccessToken)
                .build();
    }

    @Override
    public void saveRefreshToken(User user, String refreshToken) {
        // 기존 리프레시 토큰이 있다면 업데이트, 없다면 새로 생성
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByUser(user)
                .orElse(RefreshToken.builder()
                        .user(user)
                        .token(refreshToken)
                        .build());

        refreshTokenEntity.updateToken(refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);
    }

    @Override
    public void logout(String refreshToken) {
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RestApiException(AuthErrorStatus.INVALID_REFRESH_TOKEN));

        refreshTokenRepository.delete(refreshTokenEntity);
    }

    /**
     * 로그인 성공 시, 엑세스 토큰과 리프레쉬 토큰을 발급하고 헤더에 넣는 코드를 공통으로 묶음.
     */
    @Override
    public String processLoginSuccess(User user, HttpServletResponse response) {
        // Access Token, Refresh Token 발급
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                customUserDetails,
                null,
                List.of(new SimpleGrantedAuthority(user.getUserType().toString()))
        );

        String newAccessToken = jwtTokenProvider.generateToken(authentication).getAccessToken();
        String newRefreshToken = jwtTokenProvider.generateToken(authentication).getRefreshToken();

        // Refresh Token 저장
        saveRefreshToken(user, newRefreshToken);

        // Refresh Token을 쿠키에 설정
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", newRefreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(7 * 24 * 60 * 60) // 7일(7 * 24 * 60 * 60)
                .path("/")
                .build();

        response.setHeader("Set-Cookie", refreshTokenCookie.toString());

        return newAccessToken;
    }

    @Override
    public void sendLoginAuthMessage(PasswordFindRequest passwordFindRequest) throws Exception{
        User user = userRepository.findUserByNameAndEmailAndUserId(passwordFindRequest.getName(), passwordFindRequest.getEmail(), passwordFindRequest.getUserId())
                .orElseThrow(() -> new RestApiException(UserErrorStatus.USER_NOT_FOUND));

        String ePw = generateRandomPassword();
        MimeMessage message = createMessage(passwordFindRequest.getEmail(), ePw); // 인증 메일 생성
        try {
            user.setEncodedPassword(passwordEncoder.encode(ePw));
            mailSender.send(message); // 메일 전송
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException(); // 오류 처리
        }
    }

    @Override
    public String findId(IDFindRequest idFindRequest) throws Exception {
        User user = userRepository.findUserByNameAndEmail(idFindRequest.getName(), idFindRequest.getEmail())
                .orElseThrow(() -> new RestApiException(UserErrorStatus.USER_NOT_FOUND));

        return user.getUserId();
    }

    @Override
    public void changePassword(User user, PasswordChangeRequest passwordChangeRequest) throws Exception{
        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(passwordChangeRequest.getCurrentPassword(), user.getPassword())) {
            throw new RestApiException(UserErrorStatus.INVALID_PASSWORD);
        }

        // 새 비밀번호와 새 비밀번호 확인이 일치하는지 확인
        if (!passwordChangeRequest.getNewPassword().equals(passwordChangeRequest.getConfirmNewPassword())) {
            throw new RestApiException(UserErrorStatus.INVALID_PASSWORD_CONFIRM);
        }

        // 새 비밀번호 암호화
        String newEncodedPassword = passwordEncoder.encode(passwordChangeRequest.getNewPassword());
        user.setEncodedPassword(newEncodedPassword);

        userRepository.save(user); // 변경된 사용자 정보 저장
    }

    private MimeMessage createMessage(String email, String ePw) throws Exception{
        MimeMessage message = mailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, email); // 수신자 설정
        message.setSubject("쿠팜 임시 비밀번호 입니다."); // 이메일 제목

        // 이메일 본문 설정
        String msgg = "<div style='margin: 100px auto; padding: 20px; max-width: 600px; font-family: Arial, sans-serif; border: 1px solid #ddd; border-radius: 10px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);'>"
                + "<p style='font-size: 16px; color: #555; text-align: center;'>"
                + "흠집 난 과일의 달콤함과 특별함을 세상에 알리고자 하는 <strong>쿠팜</strong>입니다.</p>"
                + "<hr style='border: 0; border-top: 1px solid #eee; margin: 20px 0;'>"
                + "<p style='font-size: 16px; color: #555; text-align: center;'>아래 문자열을 비밀번호에 입력해주세요:</p>"
                + "<div style='text-align: center; margin: 20px 0;'>"
                + "<span style='display: inline-block; padding: 10px 20px; background-color: #007BFF; color: white; font-size: 24px; font-weight: bold; border-radius: 5px;'>"
                + ePw + "</span>"
                + "</div>"
                + "<p style='font-size: 14px; color: #999; text-align: center;'>"
                + "감사합니다.<br><strong>쿠팜</strong> 팀</p>"
                + "</div>";

        message.setText(msgg, "utf-8", "html"); // 이메일 내용 설정
        message.setFrom(new InternetAddress("so3659@naver.com", "KHU-FARM")); // 발신자 설정

        return message;
    }

    public static String generateRandomPassword() {
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String DIGIT = "0123456789";
        String SPECIAL_CHAR = "!@#$%^&*()_+-=[]{}|;':,.<>/?";
        String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + DIGIT + SPECIAL_CHAR;
        SecureRandom random = new SecureRandom();

        StringBuilder password = new StringBuilder(12);
        for (int i = 0; i < 12; i++) {
            int randomIndex = random.nextInt(PASSWORD_ALLOW_BASE.length());
            password.append(PASSWORD_ALLOW_BASE.charAt(randomIndex));
        }
        return password.toString();
    }
}
