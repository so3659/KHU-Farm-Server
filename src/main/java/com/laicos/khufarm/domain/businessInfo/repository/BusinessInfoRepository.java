package com.laicos.khufarm.domain.businessInfo.repository;

import com.laicos.khufarm.domain.businessInfo.entity.BusinessInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessInfoRepository extends JpaRepository<BusinessInfo, Long> {

    Boolean existsByBusinessId(String businessId);
}
