package com.laicos.khufarm.domain.address.service;

import com.laicos.khufarm.domain.address.dto.request.AddressCreateRequest;
import com.laicos.khufarm.domain.user.entity.User;

public interface AddressCommandService {

    void createAddress(User user, AddressCreateRequest addressCreateRequest);
}
