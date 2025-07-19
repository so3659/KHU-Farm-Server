package com.laicos.khufarm.domain.point.converter;

import com.laicos.khufarm.domain.point.dto.response.AttendanceHistoryResponse;
import com.laicos.khufarm.domain.point.dto.response.AttendanceResponse;
import com.laicos.khufarm.domain.point.entity.Attendance;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttendanceConverter {

    public static Attendance toAttendance(User user){
        return Attendance.builder()
                .user(user)
                .build();
    }

    public static AttendanceHistoryResponse toAttendanceHistoryResponse(Attendance attendance) {
        return AttendanceHistoryResponse.builder()
                .year(attendance.getCreatedAt().getYear())
                .month(attendance.getCreatedAt().getMonthValue())
                .day(attendance.getCreatedAt().getDayOfMonth())
                .build();
    }

    public static List<AttendanceHistoryResponse> toAttendanceHistoryResponseList(List<Attendance> attendances) {
        return attendances.stream()
                .map(AttendanceConverter::toAttendanceHistoryResponse)
                .toList();
    }

    public static AttendanceResponse toAttendanceResponse(User user, List<AttendanceHistoryResponse> attendanceHistoryResponses) {
        return AttendanceResponse.builder()
                .userId(user.getId())
                .attendanceHistoryResponses(attendanceHistoryResponses)
                .build();
    }
}
