package com.buschmais.training.jee.adapters.primary.rest.exception;

import com.buschmais.training.jee.adapters.primary.rest.dto.ErrorDTO;

import jakarta.ejb.EJBAccessException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

/**
 * Maps an {@link EJBAccessException} (caller lacks the required role) to HTTP 403 with an {@link ErrorDTO} containing
 * the exception message. Being more specific, it takes precedence over the {@link RuntimeExceptionMapper}.
 */
@Slf4j
@Provider
public class EJBAccessExceptionMapper implements ExceptionMapper<EJBAccessException> {

    @Override
    public Response toResponse(EJBAccessException exception) {
        log.warn("Access denied: {}", exception.getMessage());
        return Response.status(Response.Status.FORBIDDEN)
                .type(MediaType.APPLICATION_JSON)
                .entity(ErrorDTO.builder()
                        .message(exception.getMessage())
                        .build())
                .build();
    }

}
