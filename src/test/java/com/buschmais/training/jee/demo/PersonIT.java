package com.buschmais.training.jee.demo;

import com.buschmais.training.jee.application.PersonApplicationService;
import com.buschmais.training.jee.application.model.Person;
import jakarta.inject.Inject;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ArquillianExtension.class)
class PersonIT {

    @Inject
    private PersonApplicationService personApplicationService;

    @BeforeEach
    void setUp() {
        personApplicationService.findAll()
                .forEach(person -> personApplicationService.delete(person.getId()));
    }

    @Test
    void findPersonByIdWithoutResult() {
        assertThat(personApplicationService.findById(1L)).isNotPresent();
    }

    @Test
    void findPersonByIdWithResult() {
        Person person = new Person();
        person.setId(1L);
        person.setName("Bob");
        personApplicationService.save(person);

        assertThat(personApplicationService.findById(1L)).isPresent()
                .hasValue(person);
    }

    @Test
    void findAll() {
        assertThat(personApplicationService.findAll()).isEmpty();
    }

}
