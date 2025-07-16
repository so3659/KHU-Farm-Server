package com.laicos.khufarm.domain.fruit.dto;

import lombok.Getter;

@Getter
public class FruitReadCondition {

    private Long wholesaleRetailCategoryId;
    private Long fruitCategoryId;
    private String searchKeyword;

    public FruitReadCondition(Long wholesaleRetailCategoryId){
        this.wholesaleRetailCategoryId = wholesaleRetailCategoryId;
    }

    public FruitReadCondition(Long wholesaleRetailCategoryId, String searchKeyword) {
        this.wholesaleRetailCategoryId = wholesaleRetailCategoryId;
        this.searchKeyword = searchKeyword;
    }


    public FruitReadCondition(Long wholesaleRetailCategoryId, Long fruitCategoryId){
        this.wholesaleRetailCategoryId = wholesaleRetailCategoryId;
        this.fruitCategoryId = fruitCategoryId;
    }

    public FruitReadCondition(Long wholesaleRetailCategoryId, Long fruitCategoryId, String searchKeyword) {
        this.wholesaleRetailCategoryId = wholesaleRetailCategoryId;
        this.fruitCategoryId = fruitCategoryId;
        this.searchKeyword = searchKeyword;
    }
}
