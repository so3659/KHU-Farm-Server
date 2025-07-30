package com.laicos.khufarm.domain.inquiry.dto.response;

import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InquiryResponseWithFruit {

    private FruitResponse fruitResponse;
    private Long inquiryId;
    private String content;
    private LocalDateTime createdAt;
    private boolean isPrivate;
    private InquiryReplyResponse reply;
}
