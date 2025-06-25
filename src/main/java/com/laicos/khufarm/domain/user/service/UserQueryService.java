package com.laicos.khufarm.domain.user.service;

import com.laicos.khufarm.domain.user.entity.User;

public interface UserQueryService {

    User findUserById(Long userId);

    Boolean checkExistId(String userId);
}
