package com.laicos.khufarm.domain.fruit.dto.request;

import com.laicos.khufarm.domain.fruit.validation.annotation.ExistFruitCategory;
import com.laicos.khufarm.domain.fruit.validation.annotation.ExistWholesaleRetailCategory;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FruitAddRequest {

    @ExistFruitCategory
    @NotNull(message = "과일 카테고리 ID는 필수입니다.")
    Long fruitCategoryId;

    @ExistWholesaleRetailCategory
    @NotNull(message = "도매/소매 카테고리 ID는 필수입니다.")
    Long wholesaleRetailCategoryId;

    @NotNull(message = "가로형 이미지 URL은 필수입니다.")
    String widthImage;

    @NotNull(message = "정방형 이미지 URL은 필수입니다.")
    String squareImage;

    @NotNull(message = "상품 제목은 필수입니다.")
    String title;

    @NotNull(message = "상품 가격은 필수입니다.")
    Integer price;

    @NotNull(message = "상품 무게는 필수입니다.")
    Integer weight;

    @NotNull(message = "택배사 설정은 필수입니다.")
    private String deliveryCompany;

    @NotNull(message = "배송일 설정은 필수입니다.")
    private Integer deliveryDay;

    String description;
}
