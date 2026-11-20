package com.buschmais.training.jee.demo;

import java.net.URL;

import com.buschmais.training.jee.adapters.primary.rest.PersonDTO;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static jakarta.ws.rs.client.Entity.entity;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;
import static jakarta.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ArquillianExtension.class)
@RunAsClient
class PersonClientIT {

    @ArquillianResource
    private URL deploymentUrl;

    private Client client;

    private WebTarget target;

    @BeforeEach
    void initClient() {
        client = ClientBuilder.newClient();
        target = client.target(deploymentUrl.toString())
                .path("rest");
    }

    @AfterEach
    void closeClient() {
        if (client != null) {
            client.close();
        }
    }

    @Test
    void putAndFindById() {
        WebTarget personTarget = target.path("persons/{id}");
        assertThat(personTarget.resolveTemplate("id", 1L)
                .request(APPLICATION_JSON_TYPE)
                .get()
                .getStatus()).isEqualTo(NOT_FOUND.getStatusCode());

        PersonDTO createPersonDTO;
        try (Response putResponse = personTarget.resolveTemplate("id", 1L)
                .request(APPLICATION_JSON_TYPE)
                .put(entity(PersonDTO.builder()
                        .id(1l)
                        .name("John Doe")
                        .build(), APPLICATION_JSON_TYPE))) {
            assertThat(putResponse.getStatus()).isEqualTo(OK.getStatusCode());
            createPersonDTO = putResponse.readEntity(PersonDTO.class);
        }
        verify(createPersonDTO, 1L, "John Doe");

        Response getResponse = personTarget.resolveTemplate("id", 1L)
                .request(APPLICATION_JSON_TYPE)
                .get();
        assertThat(getResponse.getStatus()).isEqualTo(OK.getStatusCode());
    }

    private static void verify(PersonDTO personDTO, long expectedId, String expectedName) {
        assertThat(personDTO.getId()).isEqualTo(expectedId);
        assertThat(personDTO.getName()).isEqualTo(expectedName);
    }
}
