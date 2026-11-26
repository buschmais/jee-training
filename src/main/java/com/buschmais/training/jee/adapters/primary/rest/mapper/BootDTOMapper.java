package com.buschmais.training.jee.adapters.primary.rest.mapper;

import com.buschmais.training.jee.adapters.primary.rest.dto.BookDTO;
import com.buschmais.training.jee.application.model.Book;

import org.mapstruct.Mapper;

@Mapper
public interface BootDTOMapper {

    Book toDomain(BookDTO bookDTO);

    BookDTO toDTO(Book book);

}
