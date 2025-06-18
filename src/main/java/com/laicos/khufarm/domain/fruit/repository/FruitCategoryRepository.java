package com.laicos.khufarm.domain.fruit.repository;

import com.laicos.khufarm.domain.fruit.entity.category.FruitCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FruitCategoryRepository extends JpaRepository<FruitCategory, Long> {

    boolean existsById(Long id);
}
