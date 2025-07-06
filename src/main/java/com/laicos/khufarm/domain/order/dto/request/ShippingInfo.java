package com.laicos.khufarm.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ShippingInfo {

    @NotBlank(message = "주소 이름은 필수입니다.")
    private String addressName;

    @NotBlank(message = "우편번호는 필수입니다.")
    private String portCode;

    @NotBlank(message = "주소는 필수입니다.")
    private String address;

    private String detailAddress;

    @NotBlank(message = "받는 사람 이름은 필수입니다.")
    private String recipient;

    @NotBlank(message = "전화번호는 필수입니다.")
    private String phoneNumber;

    private String orderRequest;
}
