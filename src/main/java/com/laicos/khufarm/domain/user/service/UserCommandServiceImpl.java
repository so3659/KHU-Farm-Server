package com.laicos.khufarm.domain.user.service;

import com.laicos.khufarm.domain.auth.service.AuthCommandService;
import com.laicos.khufarm.domain.termsAgreement.service.TermsAgreementService;
import com.laicos.khufarm.domain.user.converter.UserConverter;
import com.laicos.khufarm.domain.user.dto.request.UserJoinRequest;
import com.laicos.khufarm.domain.user.dto.response.UserResponse;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.user.repository.UserRepository;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.MemberErrorStatus;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthCommandService authCommandService;
    private final TermsAgreementService termsAgreementService;

    @Override
    public UserResponse joinUser(Integer userType, UserJoinRequest userJoinRequest, HttpServletResponse response) {

        String email = userJoinRequest.getEmail();
        Boolean isExist = userRepository.existsByEmail(email);

        //이미 존재하는 이메일인 경우 예외처리
        if (isExist) {
            throw new RestApiException(MemberErrorStatus.EMAIL_ALREADY_EXISTS);
        }

        User user = UserConverter.toUser(userType, userJoinRequest);

        user.setEncodedPassword(passwordEncoder.encode(userJoinRequest.getPassword()));
        userRepository.save(user);

        termsAgreementService.createTermsAgreement(user, userJoinRequest.getTermsAgreed());

        //자동 로그인 처리
        String newAccessToken = authCommandService.processLoginSuccess(user, response);

        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .userType(user.getUserType().toString())
                .accessToken(newAccessToken)
                .build();
    }
}
