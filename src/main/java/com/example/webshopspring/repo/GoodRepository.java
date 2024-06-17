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
    @Query("SELECT g FROM Good g WHERE NOT EXISTS (SELECT 1 FROM Price p WHERE p.good_id.id = g.id)")
    List<Good> findGoodsWithoutPrice();

    @Query("SELECT g FROM Good g WHERE LOWER(g.goodName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Good> findByGoodNameContainingIgnoreCase(@Param("query") String query);

    @Query(value = "SELECT * FROM goods WHERE LOWER(good_name) LIKE LOWER(CONCAT('%', :goodName, '%')) LIMIT 10", nativeQuery = true)
    List<Good> findTop10ByGoodNameContainingIgnoreCase(@Param("goodName") String goodName);
}
