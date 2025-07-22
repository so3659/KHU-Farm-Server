package com.laicos.khufarm.domain.fruit.converter;

import com.laicos.khufarm.domain.cart.entity.Cart;
import com.laicos.khufarm.domain.delivery.enums.DeliveryCompany;
import com.laicos.khufarm.domain.fruit.dto.request.FruitAddRequest;
import com.laicos.khufarm.domain.fruit.dto.response.*;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.fruit.entity.category.FruitCategory;
import com.laicos.khufarm.domain.fruit.entity.category.WholesaleRetailCategory;
import com.laicos.khufarm.domain.fruit.enums.FruitStatus;
import com.laicos.khufarm.domain.order.entity.OrderDetail;
import com.laicos.khufarm.domain.seller.entity.Seller;
import com.laicos.khufarm.domain.wishList.entity.WishList;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
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

    public static FruitResponseIsWish toFruitIsWishDTO(Fruit fruit, WishList wishList) {
        return FruitResponseIsWish.builder()
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
                .isWishList(wishList != null)
                .wishListId(wishList != null ? wishList.getId() : null)
                .build();
    }



    public static List<FruitResponseIsWish> toFruitIsWishDTOList(List<Fruit> fruits, Map<Long, WishList> wishListMap) {
        return fruits.stream()
                .map(fruit -> {
                    WishList wishList = wishListMap.get(fruit.getId());
                    return toFruitIsWishDTO(fruit, wishList);
                })
                .collect(Collectors.toList());
    }

    public static FruitResponseWithCount toFruitDTOOnlyCount(Fruit fruit, Integer count) {
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
                .cartId(null)
                .build();
    }


    public static FruitResponseWithCount toFruitDTOWithCount(Fruit fruit, Cart cart) {
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
                .count(cart.getCount())
                .cartId(cart.getId())
                .build();
    }

    public static List<FruitResponseWithCount> toFruitDTOListWithCount(List<Fruit> fruits, List<Cart> cartList) {
        return IntStream.range(0, fruits.size())
                .mapToObj(i -> toFruitDTOWithCount(fruits.get(i), cartList.get(i)))
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

    public static FruitResponseWithOrder toFruitDTOWithOrder(OrderDetail orderDetail) {
        return FruitResponseWithOrder.builder()
                .id(orderDetail.getFruit().getId())
                .title(orderDetail.getFruit().getTitle())
                .widthImageUrl(orderDetail.getFruit().getWidthImageUrl())
                .squareImageUrl(orderDetail.getFruit().getSquareImageUrl())
                .price(orderDetail.getFruit().getPrice())
                .weight(orderDetail.getFruit().getWeight())
                .deliveryCompany(orderDetail.getFruit().getDeliveryCompany())
                .deliveryDay(orderDetail.getFruit().getDeliveryDay())
                .ratingSum(orderDetail.getFruit().getRatingSum())
                .ratingCount(orderDetail.getFruit().getRatingCount())
                .description(orderDetail.getFruit().getDescription())
                .stock(orderDetail.getFruit().getStock())
                .sellerId(orderDetail.getFruit().getSeller().getId())
                .brandName(orderDetail.getFruit().getSeller().getBrandName())
                .fruitCategoryId(orderDetail.getFruit().getFruitCategory().getId())
                .wholesaleRetailCategoryId(orderDetail.getFruit().getWholesaleRetailCategory().getId())
                .orderCount(orderDetail.getCount())
                .createdAt(orderDetail.getOrder().getCreatedAt())
                .orderId(orderDetail.getOrder().getId())
                .orderDetailId(orderDetail.getId())
                .build();
    }

    public static List<FruitResponseWithOrder> toFruitDTOListWithOrder(List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(FruitConverter::toFruitDTOWithOrder)
                .collect(Collectors.toList());
    }


    public static Fruit toFruit(Seller seller, FruitCategory fruitCategory, WholesaleRetailCategory wholesaleRetailCategory, FruitAddRequest fruitAddRequest) {
        return Fruit.builder()
                .title(fruitAddRequest.getTitle())
                .widthImageUrl(fruitAddRequest.getWidthImage())
                .squareImageUrl(fruitAddRequest.getSquareImage())
                .price(fruitAddRequest.getPrice())
                .weight(fruitAddRequest.getWeight())
                .deliveryCompany(DeliveryCompany.fromName(fruitAddRequest.getDeliveryCompany()))
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
