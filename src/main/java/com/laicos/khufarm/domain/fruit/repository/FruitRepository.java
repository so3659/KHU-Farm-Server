package com.laicos.khufarm.domain.fruit.repository;

import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.seller.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FruitRepository extends JpaRepository<Fruit, Long>, CustomFruitRepository {

    @Override
    Optional<Fruit> findById(Long fruitId);

    Optional<Fruit> findByIdAndSeller(Long fruitId, Seller seller);
}
