package com.encom.bookstore.mappers;

import com.encom.bookstore.dto.BookCategoryCreateDto;
import com.encom.bookstore.dto.BookCategoryDto;
import com.encom.bookstore.dto.BookCategoryUpdateDto;
import com.encom.bookstore.model.BookCategory;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Optional;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookCategoryMapper {

    BookCategoryDto bookCategoryToBookCategoryDto(BookCategory bookCategory);

    BookCategory bookCategoryCreateDtoToBookCategory(BookCategoryCreateDto bookCategoryCreateDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BookCategory bookCategoryUpdateDtoToBookCategory(BookCategoryUpdateDto bookCategoryUpdateDto,
                                                     @MappingTarget BookCategory bookCategory);

    default <T> T mapOptionalToType(Optional<T> optional) {
        return optional.orElse(null);
    }
}
