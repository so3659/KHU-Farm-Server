package com.laicos.khufarm.domain.termsAgreement.repository;

import com.laicos.khufarm.domain.termsAgreement.entitiy.TermsConditions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TermsConditionsRepository extends JpaRepository<TermsConditions, Long> {

    Optional<TermsConditions> findTermsConditionsById(Long id);
}
