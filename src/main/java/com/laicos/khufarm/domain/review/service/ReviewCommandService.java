package com.laicos.khufarm.domain.review.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.laicos.khufarm.domain.review.dto.request.ReviewReplyRequest;
import com.laicos.khufarm.domain.review.dto.request.ReviewRequest;
import com.laicos.khufarm.domain.user.entity.User;

public interface ReviewCommandService {

    void addReview(User user, ReviewRequest reviewRequest, Long orderDetailId);
    void addReviewReply(User user, ReviewReplyRequest reviewReplyRequest, Long reviewId) throws FirebaseMessagingException;
}
