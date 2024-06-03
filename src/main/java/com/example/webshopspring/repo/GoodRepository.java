package com.example.webshopspring.repo;

import com.example.webshopspring.model.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoodRepository extends JpaRepository<Good, Long> {
    @Query("SELECT g FROM Good g Where g.id = :id")
    Optional<Good> findById(@Param("id") long id);

    @Query("SELECT g FROM Good g WHERE g.goodName = :goodName")
    Optional<Good> findByGoodName(@Param("goodName") String goodName);

    @Query("SELECT g FROM Good g WHERE NOT EXISTS (SELECT 1 FROM Price p WHERE p.good_id.id = g.id)")
    List<Good> findGoodsWithoutPrice();
}
