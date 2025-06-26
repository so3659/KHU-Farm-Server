package com.laicos.khufarm.domain.businessInfo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BusinessInfoDto {

    @NotNull(message = "대표자 성명은 필수 입력값입니다.")
    private String businessName;

    @Pattern(regexp = "^\\d{10}$", message = "'-'등의 기호를 제외한 10자리 숫자를 입력하세요.")
    @NotNull(message = "사업자 등록번호는 필수 입력값입니다.")
    private String businessId;

    @Pattern(regexp = "^\\d{8}$", message = "YYYYMMDD 형식의 8자리 숫자를 입력하세요.")
    @NotNull(message = "개업 일자는 필수 입력값입니다.")
    private String openDate;
}
