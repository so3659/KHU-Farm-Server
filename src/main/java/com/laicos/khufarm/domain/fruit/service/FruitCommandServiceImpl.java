package com.laicos.khufarm.domain.fruit.service;

import com.laicos.khufarm.domain.fruit.converter.FruitConverter;
import com.laicos.khufarm.domain.fruit.dto.request.FruitAddRequest;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.fruit.entity.category.FruitCategory;
import com.laicos.khufarm.domain.fruit.entity.category.WholesaleRetailCategory;
import com.laicos.khufarm.domain.fruit.repository.FruitCategoryRepository;
import com.laicos.khufarm.domain.fruit.repository.FruitRepository;
import com.laicos.khufarm.domain.fruit.repository.WholesaleRetailCategoryRepository;
import com.laicos.khufarm.domain.seller.entity.Seller;
import com.laicos.khufarm.domain.seller.repository.SellerRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.FruitErrorStatus;
import com.laicos.khufarm.global.common.exception.code.status.SellerErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FruitCommandServiceImpl implements FruitCommandService{

    private final FruitRepository fruitRepository;
    private final SellerRepository sellerRepository;
    private final FruitCategoryRepository fruitCategoryRepository;
    private final WholesaleRetailCategoryRepository wholesaleRetailCategoryRepository;

    @Override
    public void addFruit(User user, FruitAddRequest fruitAddRequest){

        Seller seller = sellerRepository.findByUser(user)
                .orElseThrow(() -> new RestApiException(SellerErrorStatus.SELLER_NOT_FOUND));

        FruitCategory fruitCategory = fruitCategoryRepository.findById(fruitAddRequest.getFruitCategoryId())
                .orElseThrow(() -> new RestApiException(FruitErrorStatus.FRUIT_CATEGORY_NOT_FOUND));

        WholesaleRetailCategory wholesaleRetailCategory = wholesaleRetailCategoryRepository.findById(fruitAddRequest.getWholesaleRetailCategoryId())
                .orElseThrow(() -> new RestApiException(FruitErrorStatus.WHOLESALE_RETAIL_CATEGORY_NOT_FOUND));


        Fruit fruit = FruitConverter.toFruit(seller, fruitCategory, wholesaleRetailCategory, fruitAddRequest);

        fruitRepository.save(fruit);
    }
}
