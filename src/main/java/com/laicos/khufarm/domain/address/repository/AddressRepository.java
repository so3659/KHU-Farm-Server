package com.laicos.khufarm.domain.address.repository;

import com.laicos.khufarm.domain.address.entity.Address;
import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>, CustomAddressRepository {

    Optional<Address> findByUserAndIsDefaultTrue(User user);
    Optional<Address> findByUserAndId(User user, Long id);
}
