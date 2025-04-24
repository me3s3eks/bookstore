package com.encom.bookstore.mappers;

import com.encom.bookstore.dto.BookBaseInfoDto;
import com.encom.bookstore.dto.BookCreateDto;
import com.encom.bookstore.dto.BookDto;
import com.encom.bookstore.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {
    BookBaseInfoDto bookToBookBaseInfoDto(Book book);

    BookDto bookToBookDto(Book book);

    Book bookCreateDtoToBook(BookCreateDto bookCreateDto);
}
