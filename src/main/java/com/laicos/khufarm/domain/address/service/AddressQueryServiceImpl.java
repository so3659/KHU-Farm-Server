package com.laicos.khufarm.domain.address.service;

import com.laicos.khufarm.domain.address.dto.response.AddressResponse;
import com.laicos.khufarm.domain.address.repository.AddressRepository;
import com.laicos.khufarm.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressQueryServiceImpl implements AddressQueryService{

    private final AddressRepository addressRepository;

    @Override
    public Slice<AddressResponse> getAddress(User user, Long cursorId, Pageable pageable){

        return addressRepository.getAddress(user, cursorId, pageable);
    }
}
