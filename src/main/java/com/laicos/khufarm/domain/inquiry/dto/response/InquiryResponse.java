package com.laicos.khufarm.domain.inquiry.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InquiryResponse {
    private String content;
    private LocalDateTime createdAt;
    private boolean isPrivate;
    private InquiryReplyResponse reply;
}
