package com.encom.bookstore.mappers;

import com.encom.bookstore.dto.AuthorBaseInfoDto;
import com.encom.bookstore.dto.AuthorRequestDto;
import com.encom.bookstore.dto.AuthorDto;
import com.encom.bookstore.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorMapper {

    AuthorBaseInfoDto authorToAuthorBaseInfoDto(Author author);

    AuthorDto authorToAuthorDto(Author author);

    Author authorRequestDtoToAuthor(AuthorRequestDto authorRequestDto);

    Author updateAuthorFromDto(AuthorRequestDto authorRequestDto, @MappingTarget Author author);
}
