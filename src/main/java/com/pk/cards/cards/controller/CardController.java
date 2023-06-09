package com.pk.cards.cards.controller;

import com.pk.cards.cards.dto.CardDTO;
import com.pk.cards.cards.dto.CardSearchCriteria;
import com.pk.cards.cards.model.Card;
import com.pk.cards.cards.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<List<CardDTO>> searchCards(@RequestBody CardSearchCriteria searchCriteria) {
        List<CardDTO> cards = cardService.searchCards(searchCriteria);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/getcard/{cardId}")
    public ResponseEntity<Card> getCards(@PathVariable int cardId) {
        Card cardDTO = cardService.getCardByIdAndUser(cardId);
        if (cardDTO != null) {
            return ResponseEntity.ok(cardDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cards/{cardId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public String getCard(@PathVariable int cardId) {
        // Retrieve the authenticated user's email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        /** String userEmail = userDetails.getUsername();

        // Check the user's role to determine the access level
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_Admin"))) {
            // Admin can access any card
            Card cardDTO = cardService.getCardById(cardId);
            return ResponseEntity.ok(cardDTO);
        } else {
            // Member can access only their own cards
            Card cardDTO = cardService.getCardByUserAndId(userEmail, cardId);
            if (cardDTO != null) {
                return ResponseEntity.ok(cardDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        }  **/
        return "";
    }


}
