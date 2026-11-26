package com.buschmais.training.jee.application.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.buschmais.training.jee.application.BookApplicationService;
import com.buschmais.training.jee.application.BookRepository;
import com.buschmais.training.jee.application.model.Book;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BookApplicationServiceImpl implements BookApplicationService {

    private final BookRepository bookRepository;

    @Override
    public UUID create(Book book) {
        return bookRepository.create(book);
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return bookRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        bookRepository.delete(id);
    }

}
