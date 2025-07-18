package com.laicos.khufarm.domain.fruit.converter;

import com.laicos.khufarm.domain.fruit.dto.request.FruitAddRequest;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseWithCount;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseWithWishListId;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.fruit.entity.category.FruitCategory;
import com.laicos.khufarm.domain.fruit.entity.category.WholesaleRetailCategory;
import com.laicos.khufarm.domain.fruit.enums.FruitStatus;
import com.laicos.khufarm.domain.seller.entity.Seller;
import com.laicos.khufarm.domain.wishList.entity.WishList;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class FruitConverter {

    public static FruitResponse toFruitDTO(Fruit fruit) {
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
                .stock(fruit.getStock())
                .sellerId(fruit.getSeller().getId())
                .brandName(fruit.getSeller().getBrandName())
                .fruitCategoryId(fruit.getFruitCategory().getId())
                .wholesaleRetailCategoryId(fruit.getWholesaleRetailCategory().getId())
                .build();
    }


    public static List<FruitResponse> toFruitDTOList(List<Fruit> fruits) {
        return fruits.stream()
                .map(FruitConverter::toFruitDTO)
                .collect(Collectors.toList());
    }

    public static FruitResponseWithCount toFruitDTOWithCount(Fruit fruit, Integer count) {
        return FruitResponseWithCount.builder()
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
                .stock(fruit.getStock())
                .sellerId(fruit.getSeller().getId())
                .brandName(fruit.getSeller().getBrandName())
                .fruitCategoryId(fruit.getFruitCategory().getId())
                .wholesaleRetailCategoryId(fruit.getWholesaleRetailCategory().getId())
                .count(count)
                .build();
    }

    public static List<FruitResponseWithCount> toFruitDTOListWithCount(List<Fruit> fruits, List<Integer> countList) {
        return IntStream.range(0, fruits.size())
                .mapToObj(i -> toFruitDTOWithCount(fruits.get(i), countList.get(i)))
                .collect(Collectors.toList());
    }

    public static FruitResponseWithWishListId toFruitDTOWithWishList(Fruit fruit, WishList wishList) {
        return FruitResponseWithWishListId.builder()
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
                .stock(fruit.getStock())
                .sellerId(fruit.getSeller().getId())
                .brandName(fruit.getSeller().getBrandName())
                .fruitCategoryId(fruit.getFruitCategory().getId())
                .wholesaleRetailCategoryId(fruit.getWholesaleRetailCategory().getId())
                .wishListId(wishList.getId())
                .build();
    }

    public static List<FruitResponseWithWishListId> toFruitDTOListWithCWishList(List<Fruit> fruits, List<WishList> wishListList) {
        return IntStream.range(0, fruits.size())
                .mapToObj(i -> toFruitDTOWithWishList(fruits.get(i), wishListList.get(i)))
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
                .stock(fruitAddRequest.getStock())
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
