package com.example.webshopspring.repo;

import com.example.webshopspring.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT c FROM Card c WHERE c.user.email = :user_email ")
    Optional<Card> findCardByUserEmail(@Param("user_email") String user_email);

    @Modifying
    @Query("DELETE FROM Card c where c.good.id = :good_id and c.user.id = :user_id")
    void deleteGoodFromUserCard(@Param("good_id")Long good_id, @Param("user_id") Long user_id);

}
