package com.laicos.khufarm.domain.user.dto.request;

import com.laicos.khufarm.domain.termsAgreement.dto.TermsAgreementDTO;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserJoinRequest {

    private String name;
    private String email;
    private String userId;
    private String phoneNumber;
    private String password;
    private String passwordConfirm;
    private double version;
    private List<TermsAgreementDTO> termsAgreed;
}
