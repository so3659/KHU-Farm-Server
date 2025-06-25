package com.laicos.khufarm.domain.termsAgreement.service;

import com.laicos.khufarm.domain.termsAgreement.dto.TermsAgreementDTO;
import com.laicos.khufarm.domain.termsAgreement.entitiy.Agreement;
import com.laicos.khufarm.domain.termsAgreement.entitiy.TermsConditions;
import com.laicos.khufarm.domain.termsAgreement.enums.TermsEssential;
import com.laicos.khufarm.domain.termsAgreement.repository.TermsConditionsRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.TermsConditionsErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TermsAgreementServiceImpl implements TermsAgreementService {

    private final TermsConditionsRepository termsConditionsRepository;

    @Override
    public void createTermsAgreement(User user, List<TermsAgreementDTO> termsAgreed){

        //필수 약관 동의 여부 검증
        validateAllRequiredTermsAgreements(termsAgreed);

        List<Agreement> agreements = termsAgreed.stream()
                .map(agree -> {
                    TermsConditions termsConditions = getTermsConditions(agree.getTermsConditionsId());
                    return Agreement.builder()
                            .user(user)
                            .termsConditions(termsConditions)
                            .agreement(agree.getAgreed())
                            .build();
                })
                .toList();
    }

    @Override
    public TermsConditions getTermsConditions(Long termsConditionsId) {
        return termsConditionsRepository.findTermsConditionsById(termsConditionsId)
                .orElseThrow(() -> new RestApiException(TermsConditionsErrorStatus.TERMS_CONDITIONS_NOT_FOUND));
    }

    @Override
    public void validateAllRequiredTermsAgreements(List<TermsAgreementDTO> termsAgreed) {
        termsAgreed.forEach(agree ->
                validateRequiredTermsAgreement(agree.getAgreed(), agree.getTermsConditionsId())
        );
    }

    @Override
    public void validateRequiredTermsAgreement(Boolean agreed, Long termsConditionsId) {

        TermsConditions termsConditions = termsConditionsRepository.findTermsConditionsById(termsConditionsId)
                .orElseThrow(() -> new RestApiException(TermsConditionsErrorStatus.TERMS_CONDITIONS_NOT_FOUND));

        if (termsConditions.getTermsEssential() == TermsEssential.ESSENTIAL && !agreed) {
            throw new RestApiException(TermsConditionsErrorStatus.TERMS_CONDITIONS_NOT_AGREED);
        }
    }
}
