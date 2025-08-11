package com.laicos.khufarm.domain.user.service;

import com.laicos.khufarm.domain.auth.service.AuthCommandService;
import com.laicos.khufarm.domain.businessInfo.service.BusinessInfoCommandService;
import com.laicos.khufarm.domain.termsAgreement.service.TermsAgreementService;
import com.laicos.khufarm.domain.user.converter.UserConverter;
import com.laicos.khufarm.domain.user.dto.request.BusinessUserJoinRequest;
import com.laicos.khufarm.domain.user.dto.request.FarmerUserJoinRequest;
import com.laicos.khufarm.domain.user.dto.request.IndividualUserJoinRequest;
import com.laicos.khufarm.domain.user.dto.request.UserLoginRequest;
import com.laicos.khufarm.domain.user.dto.response.UserResponse;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.user.enums.UserStatus;
import com.laicos.khufarm.domain.user.repository.UserRepository;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.UserErrorStatus;
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
    private final BusinessInfoCommandService businessInfoCommandService;

    @Override
    public UserResponse joinIndividualUser(IndividualUserJoinRequest individualUserJoinRequest, HttpServletResponse response) {

        String email = individualUserJoinRequest.getEmail();
        Boolean isExist = userRepository.existsByEmail(email);

        //이미 존재하는 이메일인 경우 예외처리
        if (isExist) {
            throw new RestApiException(UserErrorStatus.EMAIL_ALREADY_EXISTS);
        }

        // 비밀번호와 비밀번호 확인이 일치하지 않는 경우 예외처리
        if (!individualUserJoinRequest.getPassword().equals(individualUserJoinRequest.getPasswordConfirm())) {
            throw new RestApiException(UserErrorStatus.INVALID_PASSWORD);
        }

        User user = UserConverter.toIndividualUser(individualUserJoinRequest);

        user.setEncodedPassword(passwordEncoder.encode(individualUserJoinRequest.getPassword()));
        userRepository.save(user);

        termsAgreementService.createTermsAgreement(user, individualUserJoinRequest.getTermsAgreed());

        //자동 로그인 처리
        String newAccessToken = authCommandService.processLoginSuccess(user, response);

        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .userType(user.getUserType().toString())
                .accessToken(newAccessToken)
                .build();
    }

    @Override
    public UserResponse joinBusinessUser(BusinessUserJoinRequest businessUserJoinRequest, HttpServletResponse response) {

        String email = businessUserJoinRequest.getEmail();
        Boolean isExist = userRepository.existsByEmail(email);

        //이미 존재하는 이메일인 경우 예외처리
        if (isExist) {
            throw new RestApiException(UserErrorStatus.EMAIL_ALREADY_EXISTS);
        }

        // 비밀번호와 비밀번호 확인이 일치하지 않는 경우 예외처리
        if (!businessUserJoinRequest.getPassword().equals(businessUserJoinRequest.getPasswordConfirm())) {
            throw new RestApiException(UserErrorStatus.INVALID_PASSWORD);
        }

        User user = UserConverter.toBusinessUser(businessUserJoinRequest);

        // 비즈니스 정보 유효성 검사
        businessInfoCommandService.validateBusinessInfo(businessUserJoinRequest.getBusinessInfoDto());

        user.setEncodedPassword(passwordEncoder.encode(businessUserJoinRequest.getPassword()));
        userRepository.save(user);

        // 약관 동의 정보 저장
        termsAgreementService.createTermsAgreement(user, businessUserJoinRequest.getTermsAgreed());

        // 비즈니스 정보 저장
        businessInfoCommandService.saveBusinessInfo(businessUserJoinRequest.getBusinessInfoDto(), user);

        //자동 로그인 처리
        String newAccessToken = authCommandService.processLoginSuccess(user, response);

        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .userType(user.getUserType().toString())
                .accessToken(newAccessToken)
                .build();
    }

    @Override
    public UserResponse joinFarmerUser(FarmerUserJoinRequest farmerUserJoinRequest, HttpServletResponse response) {

        String email = farmerUserJoinRequest.getEmail();
        Boolean isExist = userRepository.existsByEmail(email);

        //이미 존재하는 이메일인 경우 예외처리
        if (isExist) {
            throw new RestApiException(UserErrorStatus.EMAIL_ALREADY_EXISTS);
        }

        // 비밀번호와 비밀번호 확인이 일치하지 않는 경우 예외처리
        if (!farmerUserJoinRequest.getPassword().equals(farmerUserJoinRequest.getPasswordConfirm())) {
            throw new RestApiException(UserErrorStatus.INVALID_PASSWORD);
        }

        User user = UserConverter.toFarmerUser(farmerUserJoinRequest);

        // 비즈니스 정보 유효성 검사
        businessInfoCommandService.validateBusinessInfo(farmerUserJoinRequest.getBusinessInfoDto());

        user.setEncodedPassword(passwordEncoder.encode(farmerUserJoinRequest.getPassword()));
        userRepository.save(user);

        // 약관 동의 정보 저장
        termsAgreementService.createTermsAgreement(user, farmerUserJoinRequest.getTermsAgreed());

        // 비즈니스 정보 저장
        businessInfoCommandService.saveBusinessInfo(farmerUserJoinRequest.getBusinessInfoDto(), user);

        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .userType(user.getUserType().toString())
                .accessToken(null)
                .build();
    }

    @Override
    public UserResponse loginUser(UserLoginRequest userLoginRequest, HttpServletResponse response) {

        String userId = userLoginRequest.getUserId();
        String password = userLoginRequest.getPassword();

        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new RestApiException(UserErrorStatus.USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RestApiException(UserErrorStatus.INVALID_PASSWORD);
        }

        // 대기 상태의 유저의 경우 예외 처리
        if (user.getUserStatus() == UserStatus.STAND_BY) {
            throw new RestApiException(UserErrorStatus.USER_STAND_BY);
        }

        //자동 로그인 처리
        String newAccessToken = authCommandService.processLoginSuccess(user, response);

        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .userType(user.getUserType().toString())
                .accessToken(newAccessToken)
                .build();
    }
}
