package com.buschmais.training.jee.adapters.primary.rest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.buschmais.training.jee.adapters.primary.rest.dto.BookDTO;
import com.buschmais.training.jee.adapters.primary.rest.mapper.BootDTOMapper;
import com.buschmais.training.jee.application.BookApplicationService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@NoArgsConstructor(force = true)
@Path("books")
public class BookResource {

    private final BookApplicationService bookApplicationService;

    private final BootDTOMapper bookDTOMapper;

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response create(BookDTO bookDTO) {
        UUID id = bookApplicationService.create(bookDTOMapper.toDomain(bookDTO));
        return Response.status(Response.Status.CREATED)
                .entity(id)
                .build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    public List<BookDTO> findByTitle(@QueryParam("title") String title) {
        return bookApplicationService.findByTitle(title)
                .stream()
                .map(book -> bookDTOMapper.toDTO(book))
                .collect(Collectors.toList());
    }

    @GET
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    public Response findById(@PathParam("id") UUID id) {
        return bookApplicationService.findById(id)
                .map(book -> Response.ok(bookDTOMapper.toDTO(book))
                        .build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND)
                        .build());
    }

    @DELETE
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    public Response delete(@PathParam("id") UUID id) {
        bookApplicationService.delete(id);
        return Response.status(Response.Status.OK)
                .entity(id)
                .build();
    }
}
