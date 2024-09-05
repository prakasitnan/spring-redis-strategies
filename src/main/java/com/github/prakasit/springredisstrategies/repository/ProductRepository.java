package com.github.prakasit.springredisstrategies.repository;

import com.github.prakasit.springredisstrategies.model.db.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query("SELECT a.id FROM ProductEntity a ")
    List<Long> findAllProductIds();
}
