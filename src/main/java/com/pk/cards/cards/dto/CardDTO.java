package com.pk.cards.cards.dto;

import com.pk.cards.cards.model.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardDTO {
    private int id;
    private String name;
    private String description;
    private String color;
    private String status;
    private Date creationDate;

    public static CardDTO fromCard(Card card) {
        return  CardDTO.builder()
                .id(card.getId())
                .name(card.getName())
                .description(card.getDescription())
                .color(card.getColor())
                .status(card.getStatus())
                .creationDate(card.getCreationDate())
                .build();
    }


}
