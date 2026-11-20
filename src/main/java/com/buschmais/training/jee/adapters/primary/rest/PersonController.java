package com.buschmais.training.jee.adapters.primary.rest;

import com.buschmais.training.jee.application.PersonApplicationService;
import com.buschmais.training.jee.application.model.Person;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.UriInfo;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static lombok.AccessLevel.PROTECTED;
import static lombok.AccessLevel.PUBLIC;

@Path("persons")
@RequiredArgsConstructor(onConstructor_ = @Inject, access = PROTECTED)
@NoArgsConstructor(force = true, access = PUBLIC)
@Slf4j
public class PersonController {

    private final PersonMapper personMapper;

    private final PersonApplicationService personApplicationService;

    private final UriInfo uriInfo;

    @GET
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    public PersonDTO findById(@PathParam("id") Long id) {
        log.info(uriInfo.getAbsolutePath()
                .toString());
        Person person = personApplicationService.findById(id)
                .orElseThrow(() -> new NotFoundException("Cannot find person with id " + id));
        return personMapper.fromDomain(person);
    }

    @PUT
    @Path("{id}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public PersonDTO save(@PathParam("id") long id, PersonDTO personDTO) {
        if (id != personDTO.getId()) {
            throw new BadRequestException("Person id mismatch");
        }
        Person person = personApplicationService.save(personMapper.toDomain(personDTO));
        return personMapper.fromDomain(person);
    }

}
