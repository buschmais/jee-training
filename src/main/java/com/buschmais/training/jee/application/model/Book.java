package com.buschmais.training.jee.application.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQueries(
        @NamedQuery(name="findBooksByTitle", query = "select b from Book b where :title is null or b.title = :title")
)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String isbn;

}
