package com.buschmais.training.jee.adapters.secondary.jpa;

import com.buschmais.training.jee.application.PersonRepository;
import com.buschmais.training.jee.application.model.Person;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject, access = PROTECTED)
public class PersonRepositoryImpl implements PersonRepository {

    private final EntityManager entityManager;

    @Override
    public Optional<Person> findById(long id) {
        return ofNullable(entityManager.find(Person.class, id));
    }

    @Override
    public List<Person> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> query = criteriaBuilder.createQuery(Person.class);
        return entityManager.createQuery(query.select(query.from(Person.class)))
                .getResultList();
    }

    @Override
    public Person save(Person person) {
        return entityManager.merge(person);
    }

    @Override
    public void delete(Person person) {
        entityManager.remove(person);
    }
}
