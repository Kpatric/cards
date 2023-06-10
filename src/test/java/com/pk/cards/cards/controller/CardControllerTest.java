package com.pk.cards.cards.controller;

import com.pk.cards.cards.model.Card;
import com.pk.cards.cards.model.User;
import com.pk.cards.cards.service.CardService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Patrick Muriithi
 * @created 6/10/2023 - 11:05 AM
 * @project cards
 */
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardControllerTest {
    @Mock
    private CardService cardService;

    @InjectMocks
    private CardController cardController;

    @Test
    public void testDeleteCard_Success() {
        int cardId = 1;
        String userEmail = "user@example.com";
        Card card = new Card();
        card.setId(cardId);
        card.setUser(new User());
        when(cardService.getCardByUserAndId(any(String.class), eq(cardId))).thenReturn(card);
        ResponseEntity<Void> response = cardController.deleteCard(cardId, createMockAuthentication(userEmail));

        verify(cardService, times(1)).deleteCard(card);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteCard_CardNotFound() {
        int cardId = 1;
        String userEmail = "user@example.com";
        when(cardService.getCardByUserAndId(any(String.class), eq(cardId))).thenReturn(null);
        ResponseEntity<Void> response = cardController.deleteCard(cardId, createMockAuthentication(userEmail));

        verify(cardService, never()).deleteCard(any(Card.class));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private Authentication createMockAuthentication(String userEmail) {
        User user = new User();
        user.setEmail(userEmail);
        return new UsernamePasswordAuthenticationToken(user, null);
    }

    @Test
    void createCard() {
    }

    @Test
    void searchCards() {
    }

    @Test
    void updateCard() {
    }

    @Test
    void getCard() {
    }

}

