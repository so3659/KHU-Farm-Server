package com.laicos.khufarm.domain.openfeign.dto.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BusinessInfoConfirmResponse {

    @JsonProperty("status_code")
    private String statusCode;

    @JsonProperty("request_cnt")
    private Integer requestCnt;

    @JsonProperty("valid_cnt")
    private Integer validCnt;

    private List<BusinessData> data;

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class BusinessData {

        @JsonProperty("b_no")
        private String bNo;

        private String valid;

        @JsonProperty("valid_msg")
        private String validMsg;

        @JsonProperty("request_param")
        private RequestParam requestParam;
        private Status status;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class RequestParam {

        @JsonProperty("b_no")
        private String bNo;

        @JsonProperty("start_dt")
        private String startDt;

        @JsonProperty("p_nm")
        private String pNm;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Status {

        @JsonProperty("b_no")
        private String bNo;

        @JsonProperty("b_stt")
        private String bStt;

        @JsonProperty("b_stt_cd")
        private String bSttCd;

        @JsonProperty("tax_type")
        private String taxType;

        @JsonProperty("tax_type_cd")
        private String taxTypeCd;

        @JsonProperty("end_dt")
        private String endDt;

        @JsonProperty("utcc_yn")
        private String utccYn;

        @JsonProperty("tax_type_change_dt")
        private String taxTypeChangeDt;

        @JsonProperty("invoice_apply_dt")
        private String invoiceApplyDt;

        @JsonProperty("rbf_tax_type")
        private String rbfTaxType;

        @JsonProperty("rbf_tax_type_cd")
        private String rbfTaxTypeCd;
    }
}
