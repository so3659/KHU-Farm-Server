package com.laicos.khufarm.domain.review.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReviewResponse {

    private String title;
    private Long userId;
    private String content;
    private String imageUrl;
    private Integer rating;
    private LocalDateTime createdAt;
    private ReviewReplyResponse reply;
}
