package com.laicos.khufarm.domain.seller.service;

import com.laicos.khufarm.domain.seller.dto.request.SellerAddRequest;
import com.laicos.khufarm.domain.user.entity.User;

public interface SellerCommandService {

    void addSeller(User user, SellerAddRequest sellerAddRequest);
}
