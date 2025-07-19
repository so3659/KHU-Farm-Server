package com.laicos.khufarm.domain.address.converter;

import com.laicos.khufarm.domain.address.dto.request.AddressCreateRequest;
import com.laicos.khufarm.domain.address.dto.response.AddressResponse;
import com.laicos.khufarm.domain.address.entity.Address;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressConverter {

    public static Address toAddress(User user, AddressCreateRequest addressCreateRequest) {
        return Address.builder()
                .addressName(addressCreateRequest.getAddressName())
                .portCode(addressCreateRequest.getPortCode())
                .address(addressCreateRequest.getAddress())
                .detailAddress(addressCreateRequest.getDetailAddress())
                .isDefault(addressCreateRequest.isDefaultAddress())
                .recipient(addressCreateRequest.getRecipient())
                .phoneNumber(addressCreateRequest.getPhoneNumber())
                .user(user)
                .build();
    }

    public static AddressResponse toAddressResponse(Address address) {
        return AddressResponse.builder()
                .addressId(address.getId())
                .addressName(address.getAddressName())
                .portCode(address.getPortCode())
                .address(address.getAddress())
                .detailAddress(address.getDetailAddress())
                .isDefault(address.isDefault())
                .recipient(address.getRecipient())
                .phoneNumber(address.getPhoneNumber())
                .build();
    }

    public static List<AddressResponse> toAddressResponseList(List<Address> addressList) {
        return addressList.stream()
                .map(AddressConverter::toAddressResponse)
                .toList();
    }
}
