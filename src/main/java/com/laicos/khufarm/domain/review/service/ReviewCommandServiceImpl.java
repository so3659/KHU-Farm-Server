package com.laicos.khufarm.domain.review.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.fruit.repository.FruitRepository;
import com.laicos.khufarm.domain.image.entity.Image;
import com.laicos.khufarm.domain.image.enums.ImageStatus;
import com.laicos.khufarm.domain.image.repository.ImageRepository;
import com.laicos.khufarm.domain.notification.dto.request.FCMRequest;
import com.laicos.khufarm.domain.notification.service.NotificationCommandService;
import com.laicos.khufarm.domain.order.entity.OrderDetail;
import com.laicos.khufarm.domain.order.repository.OrderDetailRepository;
import com.laicos.khufarm.domain.review.converter.ReviewConverter;
import com.laicos.khufarm.domain.review.converter.ReviewReplyConverter;
import com.laicos.khufarm.domain.review.dto.request.ReviewReplyRequest;
import com.laicos.khufarm.domain.review.dto.request.ReviewRequest;
import com.laicos.khufarm.domain.review.entitiy.Review;
import com.laicos.khufarm.domain.review.repository.ReviewRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.FruitErrorStatus;
import com.laicos.khufarm.global.common.exception.code.status.ImageErrorStatus;
import com.laicos.khufarm.global.common.exception.code.status.OrderErrorStatus;
import com.laicos.khufarm.global.common.exception.code.status.ReviewErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewCommandServiceImpl implements ReviewCommandService{

    private final ReviewRepository reviewRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final FruitRepository fruitRepository;
    private final ImageRepository imageRepository;
    private final NotificationCommandService notificationCommandService;

    @Override
    public void addReview(User user, ReviewRequest reviewRequest, Long orderDetailId){

        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new RestApiException(OrderErrorStatus.ORDER_DETAIL_NOT_FOUND));

        if(orderDetail.isReviewed()){
            throw new RestApiException(ReviewErrorStatus.ALREADY_REVIEWED);
        }

        orderDetail.updateReviewStatus(true);

        Review review = ReviewConverter.toReview(user, reviewRequest, orderDetail);

        Fruit fruit = fruitRepository.findById(orderDetail.getFruit().getId())
                .orElseThrow(() -> new RestApiException(FruitErrorStatus.FRUIT_NOT_FOUND));

        fruit.updateRating(reviewRequest.getRating());

        orderDetail.addReview(review);

        Image image = imageRepository.findByImageUrl(reviewRequest.getImageUrl())
                .orElseThrow(() -> new RestApiException(ImageErrorStatus.IMAGE_NOT_FOUND));

        image.updateImageStatus(ImageStatus.USED);

        reviewRepository.save(review);
    }

    @Override
    public void addReviewReply(User user, ReviewReplyRequest reviewReplyRequest, Long reviewId) throws FirebaseMessagingException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RestApiException(ReviewErrorStatus.REVIEW_NOT_FOUND));

        review.addReviewReply(ReviewReplyConverter.toReviewReply(reviewReplyRequest.getContent(), review));
        review.setIsAnswered(true);
        reviewRepository.save(review);

        // 리뷰 작성자에게 알림 보내기
        FCMRequest fcmRequest = FCMRequest.builder()
                .title("리뷰 답글 알림")
                .body("작성하신 리뷰에 답글이 달렸습니다.")
                .build();

        Long userId = review.getUser().getId();

        notificationCommandService.sendMessage(userId, fcmRequest);
    }
}
