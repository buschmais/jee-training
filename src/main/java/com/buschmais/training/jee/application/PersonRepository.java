package com.buschmais.training.jee.application;

import com.buschmais.training.jee.application.model.Person;
import org.jmolecules.architecture.hexagonal.SecondaryPort;

import java.util.List;
import java.util.Optional;

@SecondaryPort
public interface PersonRepository {

    Optional<Person> findById(long id);

    List<Person> findAll();

    Person save(Person person);

    void delete(Person person);
}
