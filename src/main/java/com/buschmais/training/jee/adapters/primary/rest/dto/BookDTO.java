package com.buschmais.training.jee.adapters.primary.rest.dto;

import java.util.UUID;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private UUID id;

    private String title;

    private String isbn;

}
