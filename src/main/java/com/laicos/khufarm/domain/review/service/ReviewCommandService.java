package com.laicos.khufarm.domain.review.service;

import com.laicos.khufarm.domain.review.dto.request.ReviewRequest;
import com.laicos.khufarm.domain.user.entity.User;

public interface ReviewCommandService {

    void addReview(User user, ReviewRequest reviewRequest, Long orderDetailId);
}
