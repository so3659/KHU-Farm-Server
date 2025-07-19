package com.laicos.khufarm.domain.address.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AddressCreateRequest {

    @NotNull(message = "주소 이름은 필수입니다.")
    private String addressName;

    @NotNull(message = "우편 번호는 필수입니다.")
    private String portCode;

    @NotNull(message = "주소는 필수입니다.")
    private String address;

    @NotNull(message = "상세 주소는 필수입니다.")
    private String detailAddress;

    @NotNull(message = "기본 주소 여부는 필수입니다.")
    @JsonProperty("isDefault")
    private boolean defaultAddress;

    @NotNull(message = "수령인은 필수입니다.")
    private String recipient;

    @NotNull(message = "전화번호는 필수입니다.")
    private String phoneNumber;
}
