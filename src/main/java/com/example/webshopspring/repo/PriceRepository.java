package com.example.webshopspring.repo;

import com.example.webshopspring.model.Good;
import com.example.webshopspring.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    @Query("SELECT p FROM Price p WHERE p.id = :id")
    Optional<Price> finById(@Param("id") Long id);

    @Query("SELECT p FROM Price p WHERE p.good_id.id = :id")
    Optional<Price> findPriceForGoodId(@Param("id") Long id);

    @Query("SELECT p FROM Price p WHERE p.good_id = :good")
    List<Price> findByGood(Good good);
//Price findByPrice(Price price);
}
