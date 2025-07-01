package com.laicos.khufarm.domain.seller.repository;

import com.laicos.khufarm.domain.seller.entity.Seller;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long>, CustomSellerRepository {

    boolean existsById(Long id);

    Optional<Seller> findByUser(User user);
}
