package com.laicos.khufarm.domain.point.service;

import com.laicos.khufarm.domain.point.converter.AttendanceConverter;
import com.laicos.khufarm.domain.point.dto.response.AttendanceHistoryResponse;
import com.laicos.khufarm.domain.point.dto.response.AttendanceResponse;
import com.laicos.khufarm.domain.point.entity.Attendance;
import com.laicos.khufarm.domain.point.repository.AttendanceRepository;
import com.laicos.khufarm.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PointQueryServiceImpl implements PointQueryService{

    private final AttendanceRepository attendanceRepository;

    @Override
    public AttendanceResponse getAttendanceHistory(User user, Integer year,Integer month){

        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endOfMonth = LocalDateTime.of(
                year,
                month,
                YearMonth.of(year, month).lengthOfMonth(),
                23, 59, 59, 999_999_999
        );

        List<Attendance> attendances = attendanceRepository.findAllByUserAndCreatedAtBetween(
                user,
                startOfMonth,
                endOfMonth
        );

        List<AttendanceHistoryResponse> attendanceHistoryResponses = AttendanceConverter.toAttendanceHistoryResponseList(attendances);

        return AttendanceConverter.toAttendanceResponse(user, attendanceHistoryResponses);
    }
}
