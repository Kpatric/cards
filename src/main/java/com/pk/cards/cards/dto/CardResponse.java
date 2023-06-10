package com.pk.cards.cards.dto;

import com.pk.cards.cards.model.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Patrick Muriithi
 * @created 6/10/2023 - 10:46 AM
 * @project cards
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardResponse {
    private Card card;
    private String responseMessage;
}
