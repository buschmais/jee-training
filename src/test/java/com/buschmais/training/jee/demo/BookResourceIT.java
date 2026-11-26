package com.buschmais.training.jee.demo;

import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import com.buschmais.training.jee.adapters.primary.rest.dto.BookDTO;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static jakarta.ws.rs.client.Entity.entity;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * The user "testuser" is provided by src/test/configuration/application-users.properties.
 */
@ExtendWith(ArquillianExtension.class)
@RunAsClient
public class BookResourceIT {

    private static final String USER = "testuser";

    private static final String PASSWORD = "testpassword";

    @ArquillianResource
    private URL deploymentUrl;

    private Client client;

    private WebTarget booksTarget;

    @BeforeEach
    void beforeAll() {
        client = ClientBuilder.newClient()
                .register((ClientRequestFilter) requestContext -> requestContext.getHeaders()
                        .putSingle(HttpHeaders.AUTHORIZATION, basicAuth(USER, PASSWORD)));
        booksTarget = client.target(deploymentUrl.toString() + "/rest/books");
    }

    @AfterEach
    void afterEach() {
        if (booksTarget != null) {
            findAll().forEach(bookDTO -> delete(bookDTO.getId()));
        }
        if (client != null) {
            client.close();
        }
    }

    @Test
    void findAllBooks() {
        create("Harry Potter", "12345");

        List<BookDTO> bookDTOS = findAll();

        assertThat(bookDTOS).hasSize(1);
    }

    @Test
    void findById() {
        UUID id = create("Harry Potter", "12345");

        BookDTO bookDTO = findById(id);
        assertThat(bookDTO).isNotNull();
        assertThat(bookDTO.getTitle()).isEqualTo("Harry Potter");
    }

    @Test
    void delete() {
        UUID id = create("Harry Potter", "12345");

        delete(id);
    }

    @Test
    void deleteUnknownBook() {
        delete(UUID.randomUUID(), Response.Status.NOT_FOUND);
    }

    private UUID create(String title, String isbn) {
        Response response = booksTarget.request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .post(entity(BookDTO.builder()
                        .title(title)
                        .isbn(isbn)
                        .build(), MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
        return response.readEntity(UUID.class);
    }

    private BookDTO findById(UUID id) {
        Response getResponse = booksTarget.path("{id}")
                .resolveTemplate("id", id)
                .request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get();
        assertThat(getResponse.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
        return getResponse.readEntity(BookDTO.class);
    }

    private List<BookDTO> findAll() {
        Response response = booksTarget.request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get();

        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
        return response.readEntity(new GenericType<>() {
        });
    }

    private void delete(UUID id) {
        delete(id, Response.Status.OK);
    }

    private void delete(UUID id, Response.Status expectedStatus) {
        Response response = booksTarget.path("{id}")
                .resolveTemplate("id", id)
                .request()
                .delete();
        assertThat(response.getStatus()).isEqualTo(expectedStatus.getStatusCode());
    }

    private static String basicAuth(String user, String password) {
        return "Basic " + Base64.getEncoder()
                .encodeToString((user + ":" + password).getBytes(UTF_8));
    }
}
