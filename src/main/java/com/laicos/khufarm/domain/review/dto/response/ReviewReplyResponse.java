package com.laicos.khufarm.domain.review.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReviewReplyResponse {

    private String content;
}
