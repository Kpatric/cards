package com.pk.cards.cards.repository;
import com.pk.cards.cards.model.Card;
import com.pk.cards.cards.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    List<Card> findByUserIdAndNameContainingIgnoreCaseAndColorContainingIgnoreCaseAndStatusContainingIgnoreCaseAndCreationDateGreaterThanEqual(
            int userId, String name, String color, String status, Date creationDate, Pageable pageable);

    Page<Card> findByUserId(Integer userId, Pageable pageable);
    Card findByIdAndUser(Integer id, User user);
    void deleteByIdAndUser(Integer id, User user);

    Card findByUserEmailAndId(String userEmail, int cardId);
}
