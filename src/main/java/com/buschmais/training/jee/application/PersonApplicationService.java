package com.buschmais.training.jee.application;

import com.buschmais.training.jee.application.model.Person;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PROTECTED;

@PrimaryPort
@ApplicationScoped
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Inject, access = PROTECTED)
@NoArgsConstructor(force = true, access = PROTECTED)
public class PersonApplicationService {

    private final PersonRepository personRepository;

    public Optional<Person> findById(long id) {
        return personRepository.findById(id);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public void delete(long id) {
        personRepository.findById(id)
                .ifPresent(personRepository::delete);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }
}
