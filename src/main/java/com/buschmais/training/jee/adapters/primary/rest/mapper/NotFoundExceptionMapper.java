package com.buschmais.training.jee.adapters.primary.rest.mapper;

import com.buschmais.training.jee.adapters.primary.rest.dto.ErrorDTO;
import com.buschmais.training.jee.application.exception.NotFoundException;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

/**
 * Maps the application's {@link NotFoundException} to HTTP 404 with an {@link ErrorDTO} containing the exception
 * message. Being more specific, it takes precedence over the {@link RuntimeExceptionMapper}.
 */
@Slf4j
@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        log.warn("Resource not found: {}", exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON)
                .entity(ErrorDTO.builder()
                        .message(exception.getMessage())
                        .build())
                .build();
    }

}
