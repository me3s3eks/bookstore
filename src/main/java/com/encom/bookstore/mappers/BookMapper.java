package com.encom.bookstore.mappers;

import com.encom.bookstore.dto.BookBaseInfoDto;
import com.encom.bookstore.dto.BookDto;
import com.encom.bookstore.dto.BookRequestDto;
import com.encom.bookstore.model.Book;
import com.encom.bookstore.utils.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = StringUtils.class)
public interface BookMapper {

    BookBaseInfoDto bookToBookBaseInfoDto(Book book);

    BookDto bookToBookDto(Book book);

    @Mapping(source = "isbn", target = "isbn", qualifiedByName = "normalizeIsbn")
    Book bookRequestDtoToBook(BookRequestDto bookRequestDto);

    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "bookCategory", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(source = "isbn", target = "isbn", qualifiedByName = "normalizeIsbn")
    Book updateBookFromDtoWithoutRelatedEntities(BookRequestDto bookRequestDto, @MappingTarget Book book);
}
