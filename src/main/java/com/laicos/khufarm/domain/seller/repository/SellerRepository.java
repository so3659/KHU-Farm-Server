package com.laicos.khufarm.domain.seller.repository;

import com.laicos.khufarm.domain.seller.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long>, CustomSellerRepository {

    boolean existsById(Long id);
}
