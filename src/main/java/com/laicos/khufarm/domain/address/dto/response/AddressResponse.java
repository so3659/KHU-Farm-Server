package com.laicos.khufarm.domain.address.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AddressResponse {

    private Long addressId;
    private String addressName;
    private String portCode;
    private String address;
    private String detailAddress;
    private boolean isDefault;
    private String recipient;
    private String phoneNumber;
}
