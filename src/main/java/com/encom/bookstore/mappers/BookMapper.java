package com.encom.bookstore.mappers;

import com.encom.bookstore.dto.BookBaseInfoDto;
import com.encom.bookstore.dto.BookCreateDto;
import com.encom.bookstore.dto.BookDto;
import com.encom.bookstore.dto.BookUpdateDto;
import com.encom.bookstore.model.Book;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Optional;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    BookBaseInfoDto bookToBookBaseInfoDto(Book book);

    BookDto bookToBookDto(Book book);

    Book bookCreateDtoToBook(BookCreateDto bookCreateDto);

    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "bookCategory", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Book updateBookFromDtoWithoutRelatedEntities(BookUpdateDto bookUpdateDto, @MappingTarget Book book);

    default <T> T mapOptionalToType(Optional<T> optional) {
        return optional.orElse(null);
    }
}
