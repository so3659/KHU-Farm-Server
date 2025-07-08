package com.laicos.khufarm.domain.fruit.repository;

import com.laicos.khufarm.domain.fruit.entity.FruitRegularPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FruitRegularPriceRepository extends JpaRepository<FruitRegularPrice,Long> {

    FruitRegularPrice findByFruitCategoryId(Long fruitCategoryId);
}
