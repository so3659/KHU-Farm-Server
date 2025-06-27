package com.laicos.khufarm.domain.openfeign.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BusinessInfoConfirmRequest {

    @JsonProperty("businesses")
    private List<Business> businesses;

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Business {

        @JsonProperty("b_no")
        private String bNo;

        @JsonProperty("start_dt")
        private String startDt;

        @JsonProperty("p_nm")
        private String pNm;
    }
}
