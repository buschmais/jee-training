package com.buschmais.training.jee.demo;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that the pages under "secure" are protected by basic authentication.
 * <p>
 * The user "testuser" is provided by src/test/configuration/application-users.properties.
 */
@ExtendWith(ArquillianExtension.class)
@RunAsClient
class SecurePageIT {

    @ArquillianResource
    private URL deploymentUrl;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private URI securePage() throws Exception {
        return deploymentUrl.toURI().resolve("secure/index.xhtml");
    }

    @Test
    void withoutCredentials() throws Exception {
        HttpResponse<String> response = get(HttpRequest.newBuilder(securePage()));

        assertThat(response.statusCode()).isEqualTo(401);
        assertThat(response.headers().firstValue("WWW-Authenticate")).hasValueSatisfying(
                value -> assertThat(value).startsWithIgnoringCase("Basic"));
    }

    @Test
    void withInvalidCredentials() throws Exception {
        HttpResponse<String> response = get(HttpRequest.newBuilder(securePage())
                .header("Authorization", basicAuth("testuser", "wrongpassword")));

        assertThat(response.statusCode()).isEqualTo(401);
    }

    @Test
    void withValidCredentials() throws Exception {
        HttpResponse<String> response = get(HttpRequest.newBuilder(securePage())
                .header("Authorization", basicAuth("testuser", "testpassword")));

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).contains("Hello Private World!");
    }

    private HttpResponse<String> get(HttpRequest.Builder requestBuilder) throws IOException, InterruptedException {
        return httpClient.send(requestBuilder.GET().build(), HttpResponse.BodyHandlers.ofString());
    }

    private static String basicAuth(String user, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((user + ":" + password).getBytes(UTF_8));
    }
}
