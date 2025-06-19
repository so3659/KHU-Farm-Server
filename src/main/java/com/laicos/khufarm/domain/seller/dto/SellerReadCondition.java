package com.laicos.khufarm.domain.seller.dto;

import lombok.Getter;

@Getter
public class SellerReadCondition {

    private String searchKeyword;

    public SellerReadCondition() {
        this.searchKeyword = null;
    }

    public SellerReadCondition(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }
}
