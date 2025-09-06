package com.laicos.khufarm.domain.seller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SellerAddRequest {

    @NotNull(message = "농가 이름은 필수입니다.")
    String brandName;

    @NotNull(message = "메인 문구는 필수입니다")
    String title;

    @NotNull(message = "농가 설명은 필수입니다.")
    String description;

    @NotNull(message = "농가 사진은 필수입니다.")
    String imageUrl;
}
