package com.pk.cards.cards.controller;

import com.pk.cards.cards.dto.CardDTO;
import com.pk.cards.cards.dto.CardResponse;
import com.pk.cards.cards.dto.CardSearchCriteria;
import com.pk.cards.cards.model.Card;
import com.pk.cards.cards.service.CardService;
import com.pk.cards.cards.util.CardNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/card/")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/createcard")
    public ResponseEntity<Card> createCard(@RequestBody Card createCardRequest) {
        Card createdCard = cardService.createCard(createCardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCard);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    public ResponseEntity<List<CardDTO>> searchCards(@RequestBody CardSearchCriteria searchCriteria) {
        List<CardDTO> cards = cardService.searchCards(searchCriteria);
        return ResponseEntity.ok(cards);
    }

    @PatchMapping("/updatecards/{cardId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    public ResponseEntity<CardResponse> updateCard(@PathVariable int cardId, @RequestBody Card updateCardRequest, Authentication authentication) {
        String userEmail = authentication.getName();

        // Check if the user has access to the card
        Card card = cardService.getCardByUserAndId(userEmail, cardId);
        if (card == null) {
            String errorMessage = "Card not found";
            CardResponse response = new CardResponse(card, errorMessage);
            return ResponseEntity.ok().body(response);
        }

        // Update the card details
        cardService.updateCard(card, updateCardRequest);
        CardResponse response = new CardResponse(card, "Update Successful");
        // Return the updated card
        return ResponseEntity.ok(response);
    }


    @GetMapping("/cards/{cardId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    public ResponseEntity<Card> getCard(@PathVariable int cardId, Authentication authentication) {
        String userEmail = authentication.getName();
        return cardService.getCardData(authentication, userEmail, cardId);
    }

    @DeleteMapping("/deletecard/{cardId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    public ResponseEntity<Void> deleteCard(@PathVariable int cardId, Authentication authentication) {
        String userEmail = authentication.getName();

        // Check if the user has access to the card
        Card card = cardService.getCardByUserAndId(userEmail, cardId);
        if (card == null) {
            return ResponseEntity.notFound().build();
        }

        // Delete the card
        cardService.deleteCard(card);

        return ResponseEntity.noContent().build();
    }


}



