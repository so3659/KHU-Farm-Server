package com.laicos.khufarm.domain.delivery.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum DeliveryCompany {

    CHUNILPS("kr.chunilps", "천일택배", "+8218776606"),
    CJ_LOGISTICS("kr.cjlogistics", "CJ대한통운", "+8215881255"),
    CUPOST("kr.cupost", "CU 편의점택배", "+8215771287"),
    CVSNET("kr.cvsnet", "GS Postbox 택배", "+8215771287"),
    CWAY("kr.cway", "CWAY (Woori Express)", "+8215884899"),
    DAESIN("kr.daesin", "대신택배", "+82314620100"),
    EPOST("kr.epost", "우체국 택배", "+8215881300"),
    HANIPS("kr.hanips", "한의사랑택배", "+8216001055"),
    HANJIN("kr.hanjin", "한진택배", "+8215880011"),
    HDEXP("kr.hdexp", "합동택배", "+8218993392"),
    HOMEPICK("kr.homepick", "홈픽", "+8218000987"),
    HONAMLOGIS("kr.honamlogis", "한서호남택배", "+8218770572"),
    ILYANGLOGIS("kr.ilyanglogis", "일양로지스", "+8215880002"),
    KDEXP("kr.kdexp", "경동택배", "+8218995368"),
    KUNYOUNG("kr.kunyoung", "건영택배", "+82533543001"),
    LOGEN("kr.logen", "로젠택배", "+8215889988"),
    LOTTE("kr.lotte", "롯데택배", "+8215882121"),
    SLX("kr.slx", "SLX", "+82316375400"),
    SWGEXP("kr.swgexp", "성원글로벌카고", "+82327469984");

    private final String id;
    private final String name;
    private final String tel;

    public static DeliveryCompany fromName(String name) {
        return Arrays.stream(DeliveryCompany.values())
                .filter(c -> c.getName().equalsIgnoreCase(name)) // 대소문자 무시
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 택배사 이름입니다: " + name));
    }
}
