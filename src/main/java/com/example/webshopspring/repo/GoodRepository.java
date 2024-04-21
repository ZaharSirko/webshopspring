package com.example.webshopspring.repo;

import com.example.webshopspring.model.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodRepository extends JpaRepository<Good, Long> {
    Good findByGood(Good good);

    @Query("SELECT g FROM Good g Where g.id = :id")
    Good findById(@Param("id") long id);

    @Query("SELECT g FROM Good g WHERE g.goodName = :goodName")
    Good findByGoodName(@Param("goodName") String goodName);
}
