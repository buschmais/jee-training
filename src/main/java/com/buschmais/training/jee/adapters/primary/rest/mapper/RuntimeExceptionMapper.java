package com.buschmais.training.jee.adapters.primary.rest.mapper;

import com.buschmais.training.jee.adapters.primary.rest.dto.ErrorDTO;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

/**
 * Generic exception handler for the REST adapter: logs runtime exceptions including their stacktrace and returns an
 * {@link ErrorDTO} containing the exception message.
 */
@Slf4j
@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException exception) {
        log.error("Caught exception while processing REST request.", exception);
        return Response.status(getStatus(exception))
                .type(MediaType.APPLICATION_JSON)
                .entity(ErrorDTO.builder()
                        .message(exception.getMessage())
                        .build())
                .build();
    }

    private static int getStatus(RuntimeException exception) {
        // Preserve the status of JAX-RS exceptions (e.g. 404, 405)
        if (exception instanceof WebApplicationException webApplicationException) {
            return webApplicationException.getResponse()
                    .getStatus();
        }
        return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
    }

}
