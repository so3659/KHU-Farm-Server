package com.laicos.khufarm.domain.point.dto.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AttendanceResponse {

    private Long userId;
    List<AttendanceHistoryResponse> attendanceHistoryResponses;
}
