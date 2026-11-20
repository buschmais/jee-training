package com.buschmais.training.jee.adapters.secondary.jpa;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class JPAConfiguration {

    @PersistenceContext
    private EntityManager entityManager;

    @Produces
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
