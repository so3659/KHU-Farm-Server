package com.laicos.khufarm.domain.user.service;

import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.user.repository.UserRepository;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.UserErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService{

    private final UserRepository userRepository;

    @Override
    public User findUserById(Long memberId) {
        return userRepository.findById(memberId)
                .orElseThrow(() -> new RestApiException(UserErrorStatus.USER_NOT_FOUND));
    }

    @Override
    public Boolean checkExistId(String userId){
        Boolean exists = userRepository.existsByUserId(userId);

        //이미 존재하는 아이디인 경우 예외처리
        if (exists) {
            throw new RestApiException(UserErrorStatus.ID_ALREADY_EXISTS);
        }

        return exists;
    }
}
