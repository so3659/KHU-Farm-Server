package com.laicos.khufarm.domain.user.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserResponse {

    private String userId;
    private String name;
    private String email;
    private String userType;
    private String accessToken;
}
