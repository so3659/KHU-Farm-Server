package com.laicos.khufarm.domain.seller.dto;

import lombok.Getter;

@Getter
public class SellerReadCondition {

    private Long wholesaleRetailCategoryId;
    private Long fruitCategoryId;
    private String searchKeyword;

    public SellerReadCondition(Long wholesaleRetailCategoryId, Long fruitCategoryId){
        this.wholesaleRetailCategoryId = wholesaleRetailCategoryId;
        this.fruitCategoryId = fruitCategoryId;
    }

    public SellerReadCondition(Long wholesaleRetailCategoryId, Long fruitCategoryId, String searchKeyword) {
        this.wholesaleRetailCategoryId = wholesaleRetailCategoryId;
        this.fruitCategoryId = fruitCategoryId;
        this.searchKeyword = searchKeyword;
    }
}
