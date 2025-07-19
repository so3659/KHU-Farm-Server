package com.laicos.khufarm.domain.address.service;

import com.laicos.khufarm.domain.address.dto.response.AddressResponse;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface AddressQueryService {

    Slice<AddressResponse> getAddress(User user, Long cursorId, Pageable pageable);
}
