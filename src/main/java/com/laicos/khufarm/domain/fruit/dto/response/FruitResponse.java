package com.laicos.khufarm.domain.fruit.dto.response;


import com.laicos.khufarm.domain.delivery.enums.DeliveryCompany;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FruitResponse {

    private Long id;
    private String title;
    private String widthImageUrl;
    private String squareImageUrl;
    private Integer price;
    private Integer weight;
    private DeliveryCompany deliveryCompany;
    private Integer deliveryDay;
    private Integer ratingSum;
    private Integer ratingCount;
    private String description;
    private Integer stock;
    private Long sellerId;
    private String brandName;
    private Long fruitCategoryId;
    private Long wholesaleRetailCategoryId;
}
