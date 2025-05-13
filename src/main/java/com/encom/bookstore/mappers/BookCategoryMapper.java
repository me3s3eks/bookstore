package com.encom.bookstore.mappers;

import com.encom.bookstore.dto.BookCategoryCreateDto;
import com.encom.bookstore.dto.BookCategoryDto;
import com.encom.bookstore.model.BookCategory;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookCategoryMapper {

    BookCategoryDto bookCategoryToBookCategoryDto(BookCategory bookCategory);

    BookCategory bookCategoryCreateDtoToBookCategory(BookCategoryCreateDto bookCategoryCreateDto);
}
