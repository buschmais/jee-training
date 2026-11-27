package com.buschmais.training.jee.application.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.buschmais.training.jee.adapters.primary.rest.exception.ValidationException;
import com.buschmais.training.jee.application.BookApplicationService;
import com.buschmais.training.jee.application.BookRepository;
import com.buschmais.training.jee.application.model.Book;

import jakarta.annotation.Resource;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@DenyAll
@Stateless
@Slf4j
public class BookApplicationServiceImpl implements BookApplicationService {

    @Inject
    private BookRepository bookRepository;

    @Resource
    private SessionContext sessionContext;

    @RolesAllowed("user")
    @Override
    public UUID create(Book book) {
        return bookRepository.create(book);
    }

    @RolesAllowed("user")
    @Override
    public void update(UUID id, Book book) {
        if (id == null || !id.equals(book.getId())) {
            throw new ValidationException("Book id is not valid: " + id);
        }
        bookRepository.update(book);
    }

    @PermitAll
    @Override
    public List<Book> findByTitle(String title) {
        log.info("Current principal: {}.", sessionContext.getCallerPrincipal());
        return bookRepository.findByTitle(title);
    }

    @PermitAll
    @Override
    public Optional<Book> findById(UUID id) {
        return bookRepository.findById(id);
    }

    @RolesAllowed("user")
    @Override
    public void delete(UUID id) {
        bookRepository.delete(id);
    }

}
