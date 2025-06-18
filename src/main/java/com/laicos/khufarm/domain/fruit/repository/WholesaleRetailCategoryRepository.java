package com.laicos.khufarm.domain.fruit.repository;

import com.laicos.khufarm.domain.fruit.entity.category.WholesaleRetailCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WholesaleRetailCategoryRepository extends JpaRepository<WholesaleRetailCategory, Long> {

    boolean existsById(Long id);
}
