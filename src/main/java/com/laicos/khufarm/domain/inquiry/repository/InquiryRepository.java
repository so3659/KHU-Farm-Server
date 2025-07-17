package com.laicos.khufarm.domain.inquiry.repository;

import com.laicos.khufarm.domain.inquiry.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long>, CustomInquiryRepository {
}
