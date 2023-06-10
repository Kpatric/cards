package com.pk.cards.cards.util;

/**
 * @author Patrick Muriithi
 * @created 6/10/2023 - 10:38 AM
 * @project cards
 */
public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException(String message) {
        super(message);
    }

    public CardNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
