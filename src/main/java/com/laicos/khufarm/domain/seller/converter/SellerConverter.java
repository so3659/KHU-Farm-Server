package com.laicos.khufarm.domain.seller.converter;

import com.laicos.khufarm.domain.seller.dto.response.SellerResponse;
import com.laicos.khufarm.domain.seller.entity.Seller;
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


    public static List<SellerResponse> toDTOList(List<Seller> sellers) {
        return sellers.stream()
                .map(SellerConverter::toDTO)
                .collect(Collectors.toList());
    }
}
