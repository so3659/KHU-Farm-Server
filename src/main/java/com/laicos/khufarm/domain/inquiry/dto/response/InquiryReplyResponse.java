package com.laicos.khufarm.domain.inquiry.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InquiryReplyResponse {

    private String content;
    private String sellerName;
    private LocalDateTime createdAt;
}
