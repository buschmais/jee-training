package com.buschmais.training.jee.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.buschmais.training.jee.application.model.Book;

public interface BookRepository {

    UUID create(Book book);

    void update(Book book);

    List<Book> findByTitle(String title);

    Optional<Book> findById(UUID id);

    void delete(UUID id);
}
