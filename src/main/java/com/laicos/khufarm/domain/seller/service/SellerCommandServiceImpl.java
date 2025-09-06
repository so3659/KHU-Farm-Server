package com.laicos.khufarm.domain.seller.service;

import com.laicos.khufarm.domain.image.entity.Image;
import com.laicos.khufarm.domain.image.enums.ImageStatus;
import com.laicos.khufarm.domain.image.repository.ImageRepository;
import com.laicos.khufarm.domain.seller.converter.SellerConverter;
import com.laicos.khufarm.domain.seller.dto.request.SellerAddRequest;
import com.laicos.khufarm.domain.seller.entity.Seller;
import com.laicos.khufarm.domain.seller.repository.SellerRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.ImageErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SellerCommandServiceImpl implements SellerCommandService{

    private final SellerRepository sellerRepository;
    private final ImageRepository imageRepository;

    @Override
    public void addSeller(User user, SellerAddRequest sellerAddRequest) {

        Seller seller = SellerConverter.toSeller(user, sellerAddRequest);

        updateImageStatusToUsed(sellerAddRequest.getImageUrl());

        sellerRepository.save(seller);
    }

    private void updateImageStatusToUsed(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            return;
        }
        Image image = imageRepository.findByImageUrl(imageUrl)
                .orElseThrow(() -> new RestApiException(ImageErrorStatus.IMAGE_NOT_FOUND));
        image.updateImageStatus(ImageStatus.USED);
    }
}
