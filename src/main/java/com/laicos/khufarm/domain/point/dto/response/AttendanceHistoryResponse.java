package com.laicos.khufarm.domain.point.dto.response;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AttendanceHistoryResponse {

    private Integer year;
    private Integer month;
    private Integer day;
}
