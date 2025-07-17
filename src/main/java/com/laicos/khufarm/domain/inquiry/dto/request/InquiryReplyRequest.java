package com.laicos.khufarm.domain.inquiry.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InquiryReplyRequest {

    @NotNull(message = "내용은 필수 입력값입니다.")
    private String content;
}
