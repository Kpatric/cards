package com.pk.cards.cards.repository;
import com.pk.cards.cards.model.Card;
import com.pk.cards.cards.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

    Page<Card> findByUserId(Integer userId, Pageable pageable);
    Card findByIdAndUser(Integer id, User user);
    void deleteByIdAndUser(Integer id, User user);
    Card findByUserEmailAndId(String userEmail, int cardId);

    //used @Query here since is a complex filter and its more neat
    @Query("SELECT c FROM Card c WHERE (:userId IS NULL OR c.user.Id = :userId) " +
            "AND (:name IS NULL OR LOWER(c.name) LIKE %:name%) " +
            "AND (:color IS NULL OR LOWER(c.color) LIKE %:color%) " +
            "AND (:status IS NULL OR LOWER(c.status) LIKE %:status%) " +
            "AND (:creationDate IS NULL OR c.creationDate >= :creationDate)")
    List<Card> findCardsWithFilters(@Param("userId") Integer userId,
                                    @Param("name") String name,
                                    @Param("color") String color,
                                    @Param("status") String status,
                                    @Param("creationDate") Date creationDate,
                                    Pageable pageable);


}
