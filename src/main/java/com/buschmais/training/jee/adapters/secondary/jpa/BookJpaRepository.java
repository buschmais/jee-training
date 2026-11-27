package com.buschmais.training.jee.adapters.secondary.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.buschmais.training.jee.application.BookRepository;
import com.buschmais.training.jee.application.exception.NotFoundException;
import com.buschmais.training.jee.application.model.Book;
import com.buschmais.training.jee.application.model.Book_;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@ApplicationScoped
public class BookJpaRepository implements BookRepository {

    @Inject
    @Books
    private EntityManager entityManager;

    @Override
    public UUID create(Book book) {
        return entityManager.merge(book)
                .getId();
    }

    @Override
    public void update(Book book) {
        entityManager.merge(book);
    }

    @Override
    public List<Book> findByTitle(String title) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
        Root<Book> book = query.from(Book.class);
        if (title != null) {
            query.where(criteriaBuilder.equal(book.get(Book_.title), title));
        }
        return entityManager.createQuery(query.select(book))
                .getResultList();
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public void delete(UUID id) {
        findById(id).ifPresentOrElse(book -> entityManager.remove(book), () -> {
            throw new NotFoundException("Cannot find book with id " + id);
        });
    }

}
