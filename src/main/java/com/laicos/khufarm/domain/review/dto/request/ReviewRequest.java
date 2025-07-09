package com.laicos.khufarm.domain.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReviewRequest {

    @NotNull(message = "평점은 필수 입력값입니다.")
    @Min(1)
    @Max(5)
    private Integer rating;

    private String imageUrl;

    @NotNull(message = "제목은 필수 입력값입니다.")
    private String title;

    @NotNull(message = "내용은 필수 입력값입니다.")
    private String content;
}
