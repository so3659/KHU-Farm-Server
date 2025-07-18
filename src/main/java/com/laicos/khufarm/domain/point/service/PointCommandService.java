package com.laicos.khufarm.domain.point.service;

import com.laicos.khufarm.domain.user.entity.User;

public interface PointCommandService {

    void getReviewPoint(User user, Long reviewId);
}
