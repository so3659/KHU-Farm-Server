package com.laicos.khufarm.domain.point.service;

import com.laicos.khufarm.domain.point.converter.AttendanceConverter;
import com.laicos.khufarm.domain.point.converter.PointHistoryConverter;
import com.laicos.khufarm.domain.point.entity.Attendance;
import com.laicos.khufarm.domain.point.entity.PointHistory;
import com.laicos.khufarm.domain.point.enums.PointEventType;
import com.laicos.khufarm.domain.point.repository.AttendanceRepository;
import com.laicos.khufarm.domain.point.repository.PointHistoryRepository;
import com.laicos.khufarm.domain.review.entitiy.Review;
import com.laicos.khufarm.domain.review.repository.ReviewRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.user.repository.UserRepository;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.AttendanceErrorStatus;
import com.laicos.khufarm.global.common.exception.code.status.ReviewErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
@Transactional
public class PointCommandServiceImpl implements PointCommandService{

    private final ReviewRepository reviewRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;

    @Override
    public void getReviewPoint(User user, Long reviewId){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RestApiException(ReviewErrorStatus.REVIEW_NOT_FOUND));

        review.setIsPointed(true);

        user.updateTotalPoint(2);
        userRepository.save(user);

        PointHistory pointHistory = PointHistoryConverter.toPointHistory(2, PointEventType.REVIEW, review.getId(), user);
        pointHistoryRepository.save(pointHistory);
    }

    @Override
    public void getAttendancePoint(User user) {
        LocalDateTime startOfMonth = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), 1, 0, 0, 0);
        LocalDateTime endOfMonth = LocalDateTime.of(
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonth(),
                YearMonth.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth()).lengthOfMonth(),
                23, 59, 59, 999_999_999
        );

        boolean existsTodayAttendance = attendanceRepository.existsByUserAndCreatedAtBetween(user, startOfMonth, endOfMonth);

        if (existsTodayAttendance) {
            throw new RestApiException(AttendanceErrorStatus.ALREADY_ATTEND);
        }

        Attendance attendance = AttendanceConverter.toAttendance(user);

        attendanceRepository.save(attendance);

        user.updateTotalPoint(2);
        userRepository.save(user);

        PointHistory pointHistory = PointHistoryConverter.toPointHistory(2, PointEventType.ATTENDANCE, attendance.getId(), user);
        pointHistoryRepository.save(pointHistory);
    }
}
