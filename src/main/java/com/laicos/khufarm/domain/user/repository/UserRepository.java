package com.laicos.khufarm.domain.user.repository;

import com.laicos.khufarm.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(Long id);
    Optional<User> findUserByUserId(String userId);
    Boolean existsByUserId(String userId);
    Boolean existsByEmail(String email);

    User getUserById(Long id);

    Optional<User> findUserByNameAndEmailAndUserId(String name, String email, String userId);
    Optional<User> findUserByNameAndEmail(String name, String email);
}
