package com.pk.cards.cards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardSearchCriteria {
    private String name;
    private String color;
    private String status;
    private Date creationDate;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String sortOrder;

    public Pageable getPageable(Sort sort) {
        if (page != null && size != null) {
            return PageRequest.of(page, size,sort);
        }
        return Pageable.unpaged();
    }
}

