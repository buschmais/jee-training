package com.buschmais.training.jee.adapters.secondary.jpa;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerConfiguration {

    @PersistenceContext(unitName = "books")
    private EntityManager booksEntityManager;

    @Produces
    @Books
    public EntityManager getBooksEntityManager() {
        return booksEntityManager;
    }
}
