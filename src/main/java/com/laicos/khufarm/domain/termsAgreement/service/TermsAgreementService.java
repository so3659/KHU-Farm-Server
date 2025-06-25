package com.laicos.khufarm.domain.termsAgreement.service;

import com.laicos.khufarm.domain.termsAgreement.dto.TermsAgreementDTO;
import com.laicos.khufarm.domain.termsAgreement.entitiy.TermsConditions;
import com.laicos.khufarm.domain.user.entity.User;

import java.util.List;

public interface TermsAgreementService {

    void createTermsAgreement(User user, List<TermsAgreementDTO> termsAgreed);

    TermsConditions getTermsConditions(Long termsConditionsId);

    public void validateAllRequiredTermsAgreements(List<TermsAgreementDTO> termsAgreed);

    public void validateRequiredTermsAgreement(Boolean agreed, Long termsConditionsId);
}
