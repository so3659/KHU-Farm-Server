package com.laicos.khufarm.domain.address.service;

import com.laicos.khufarm.domain.address.converter.AddressConverter;
import com.laicos.khufarm.domain.address.dto.request.AddressCreateRequest;
import com.laicos.khufarm.domain.address.entity.Address;
import com.laicos.khufarm.domain.address.repository.AddressRepository;
import com.laicos.khufarm.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressCommandServiceImpl implements AddressCommandService{

    private final AddressRepository addressRepository;

    @Override
    public void createAddress(User user, AddressCreateRequest addressCreateRequest) {
        Address address = AddressConverter.toAddress(user, addressCreateRequest);

        if(address.isDefault()) {
            addressRepository.findByUserAndIsDefaultTrue(user)
                .ifPresent(existingAddress -> existingAddress.setDefault(false));
        }

        addressRepository.save(address);
    }
}
