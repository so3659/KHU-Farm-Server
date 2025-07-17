package com.laicos.khufarm.domain.inquiry.dto.response;

import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MyInquiryResponse {

    private FruitResponse fruitResponse;
    private InquiryResponse inquiryResponse;
}
