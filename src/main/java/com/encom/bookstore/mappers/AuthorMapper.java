package com.encom.bookstore.mappers;

import com.encom.bookstore.dto.AuthorBaseInfoDto;
import com.encom.bookstore.dto.AuthorCreateDto;
import com.encom.bookstore.dto.AuthorDto;
import com.encom.bookstore.dto.AuthorUpdateDto;
import com.encom.bookstore.model.Author;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Optional;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorMapper {

    AuthorBaseInfoDto authorToAuthorBaseInfoDto(Author author);

    AuthorDto authorToAuthorDto(Author author);

    Author authorCreateDtoToAuthor(AuthorCreateDto authorCreateDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Author updateAuthorFromDto(AuthorUpdateDto authorUpdateDto, @MappingTarget Author author);

    default <T> T mapOptionalToType(Optional<T> optional) {
        return optional.orElse(null);
    }
}
