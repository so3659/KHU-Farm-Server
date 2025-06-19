package com.laicos.khufarm.domain.seller.converter;

import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import com.laicos.khufarm.domain.seller.dto.response.SellerResponse;
import com.laicos.khufarm.domain.seller.dto.response.SellerResponseWithFruit;
import com.laicos.khufarm.domain.seller.entity.Seller;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SellerConverter {


    public static SellerResponse toDTO(Seller seller) {
        return SellerResponse.builder()
                .id(seller.getId())
                .brandName(seller.getBrandName())
                .title(seller.getTitle())
                .imageUrl(seller.getImageUrl())
                .description(seller.getDescription())
                .userId(seller.getUser().getId())
                .build();
    }

    // Seller 리스트를 SellerResponse 리스트로 변환하는 메서드
    public static List<SellerResponse> toDTOList(List<Seller> sellers) {
        return sellers.stream()
                .map(SellerConverter::toDTO)
                .collect(Collectors.toList());
    }

    // Seller와 Fruit의 관계를 고려하여 SellerResponse에 FruitResponse 리스트를 포함하는 메서드
    public static SellerResponseWithFruit toDTOList(Seller foundSeller, Slice<FruitResponse> fruitList) {

        return SellerResponseWithFruit.builder()
                .id(foundSeller.getId())
                .brandName(foundSeller.getBrandName())
                .title(foundSeller.getTitle())
                .imageUrl(foundSeller.getImageUrl())
                .description(foundSeller.getDescription())
                .userId(foundSeller.getUser().getId())
                .fruits(fruitList)
                .build();

    }
}
