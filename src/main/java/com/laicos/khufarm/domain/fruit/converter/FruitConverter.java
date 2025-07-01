package com.laicos.khufarm.domain.fruit.converter;

import com.laicos.khufarm.domain.fruit.dto.request.FruitAddRequest;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.fruit.entity.category.FruitCategory;
import com.laicos.khufarm.domain.fruit.entity.category.WholesaleRetailCategory;
import com.laicos.khufarm.domain.fruit.enums.FruitStatus;
import com.laicos.khufarm.domain.seller.entity.Seller;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FruitConverter {

    public static FruitResponse toDTO(Fruit fruit) {
        return FruitResponse.builder()
                .id(fruit.getId())
                .title(fruit.getTitle())
                .widthImageUrl(fruit.getWidthImageUrl())
                .squareImageUrl(fruit.getSquareImageUrl())
                .price(fruit.getPrice())
                .weight(fruit.getWeight())
                .deliveryCompany(fruit.getDeliveryCompany())
                .deliveryDay(fruit.getDeliveryDay())
                .ratingSum(fruit.getRatingSum())
                .ratingCount(fruit.getRatingCount())
                .description(fruit.getDescription())
                .sellerId(fruit.getSeller().getId())
                .brandName(fruit.getSeller().getBrandName())
                .fruitCategoryId(fruit.getFruitCategory().getId())
                .wholesaleRetailCategoryId(fruit.getWholesaleRetailCategory().getId())
                .build();
    }


    public static List<FruitResponse> toDTOList(List<Fruit> fruits) {
        return fruits.stream()
                .map(FruitConverter::toDTO)
                .collect(Collectors.toList());
    }

    public static Fruit toFruit(Seller seller, FruitCategory fruitCategory, WholesaleRetailCategory wholesaleRetailCategory, FruitAddRequest fruitAddRequest) {
        return Fruit.builder()
                .title(fruitAddRequest.getTitle())
                .widthImageUrl(fruitAddRequest.getWidthImage())
                .squareImageUrl(fruitAddRequest.getSquareImage())
                .price(fruitAddRequest.getPrice())
                .weight(fruitAddRequest.getWeight())
                .deliveryCompany(fruitAddRequest.getDeliveryCompany())
                .deliveryDay(fruitAddRequest.getDeliveryDay())
                .description(fruitAddRequest.getDescription())
                .fruitStatus(FruitStatus.STAND_BY)
                .seller(seller)
                .fruitCategory(fruitCategory)
                .wholesaleRetailCategory(wholesaleRetailCategory)
                .build();
    }

//    public Fruit toEntity(FruitCreateDto createDto) {
//        return Fruit.builder()
//                .title(createDto.getTitle())
//                .imageUrl(createDto.getImageUrl())
//                .price(createDto.getPrice())
//                // ... 기타 필드들
//                .build();
//    }
}
