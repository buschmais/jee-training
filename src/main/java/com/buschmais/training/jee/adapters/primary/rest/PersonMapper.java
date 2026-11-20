package com.buschmais.training.jee.adapters.primary.rest;

import com.buschmais.training.jee.application.model.Person;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper {

    PersonDTO fromDomain(Person person);

    Person toDomain(PersonDTO personDTO);

}
