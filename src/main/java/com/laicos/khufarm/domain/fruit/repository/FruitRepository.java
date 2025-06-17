package com.laicos.khufarm.domain.fruit.repository;

import com.laicos.khufarm.domain.fruit.entity.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FruitRepository extends JpaRepository<Fruit, Long>, CustomFruitRepository {
}
