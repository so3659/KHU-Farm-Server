package com.laicos.khufarm.domain.auth.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AccessTokenResponse {

    private String accessToken;

}
