package com.pk.cards.cards.service;

import com.pk.cards.cards.dto.CardDTO;
import com.pk.cards.cards.dto.CardSearchCriteria;
import com.pk.cards.cards.model.Card;
import com.pk.cards.cards.model.User;
import com.pk.cards.cards.repository.CardRepository;
import com.pk.cards.cards.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CardRepository cardRepository;

    public Card createCard(Card createCardRequest) {
        User user = userRepository.findById(getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Card card = Card.builder()
                .name(createCardRequest.getName())
                .description(createCardRequest.getDescription())
                .color(createCardRequest.getColor())
                .status("To Do")
                .user(user)
                .creationDate(new Date())
                .build();

        return cardRepository.save(card);
    }

    public List<CardDTO> searchCards(@RequestBody CardSearchCriteria searchCriteria) {
        Sort sort = Sort.by(Sort.Direction.fromString(searchCriteria.getSortOrder()), searchCriteria.getSortBy());

        List<Card> cards = cardRepository.findCardsWithFilters(
                getUserId(), searchCriteria.getName(), searchCriteria.getColor(), searchCriteria.getStatus(),
                searchCriteria.getCreationDate(), searchCriteria.getPageable(sort));
        // Map Card entities to CardDTOs
        return cards.stream()
                .map(CardDTO::fromCard)
                .collect(Collectors.toList());
    }

    private Integer getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findUserByEmail(authentication.getName()).getId();
    }

    public Card getCardByIdAndUser(int cardId) {
        User user = userRepository.findById(getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Optional<Card> optionalCard = Optional.ofNullable(cardRepository.findByIdAndUser(cardId, user));
        return optionalCard.orElse(null);
    }

    public Card getCardById(int cardId) {
        // Implement the logic to retrieve the card by ID
        return cardRepository.findById(cardId).orElse(null);
    }

    public Card getCardByUserAndId(String userEmail, int cardId) {
        // Implement the logic to retrieve the card by user email and ID
        return cardRepository.findByUserEmailAndId(userEmail, cardId);
    }

    public ResponseEntity<Card> getCardData(Authentication authentication, String userEmail, int cardId) {

        // Check the user's role to determine the access level
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            // Admin can access any card
            Card cardDTO = getCardById(cardId);
            return ResponseEntity.ok(cardDTO);
        } else {
            // Member can access only their own cards
            Card cardDTO = getCardByUserAndId(userEmail, cardId);
            if (cardDTO != null) {
                return ResponseEntity.ok(cardDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }

    public void updateCard(Card card, Card updateCardRequest) {
        if (updateCardRequest.getName() != null) {
            card.setName(updateCardRequest.getName());
        }
        if (updateCardRequest.getDescription() != null) {
            card.setDescription(updateCardRequest.getDescription());
        }
        if (updateCardRequest.getColor() != null) {
            card.setColor(updateCardRequest.getColor());
        }
        if (updateCardRequest.getStatus() != null) {
            card.setStatus(updateCardRequest.getStatus());
        }

        cardRepository.save(card);
    }

    public void deleteCard(Card card) {
        cardRepository.delete(card);
    }
}