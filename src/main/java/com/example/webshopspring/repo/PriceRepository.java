package com.example.webshopspring.repo;

import com.example.webshopspring.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
//@Query("SELECT p FROM Price p WHERE p.id = :id")
//Price finById(@Param("id") Long id);
//
//Price findByPrice(Price price);
}
