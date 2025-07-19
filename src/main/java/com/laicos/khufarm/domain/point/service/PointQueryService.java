package com.laicos.khufarm.domain.point.service;

import com.laicos.khufarm.domain.point.dto.response.AttendanceResponse;
import com.laicos.khufarm.domain.user.entity.User;

public interface PointQueryService {

    AttendanceResponse getAttendanceHistory(User user, Integer year, Integer month);
}
