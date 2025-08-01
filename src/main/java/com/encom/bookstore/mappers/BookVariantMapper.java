package com.encom.bookstore.mappers;

import com.encom.bookstore.dto.BookVariantDto;
import com.encom.bookstore.model.BookVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookVariantMapper {

    @Mapping(target = "id.bookId", source = "bookId")
    @Mapping(target = "id.bookType", source = "bookType")
    @Mapping(target = "paperBookProperties", source = "paperBookPropertiesDto")
    BookVariant bookVariantDtoToBookVariant(BookVariantDto bookVariantDto);

    @Mapping(target = "bookId", source = "id.bookId")
    @Mapping(target = "bookType", source = "id.bookType")
    @Mapping(target = "paperBookPropertiesDto", source = "paperBookProperties")
    BookVariantDto bookVariantToBookVariantDto(BookVariant bookVariant);

    @Mapping(target = "paperBookProperties", source = "paperBookPropertiesDto")
    BookVariant updateBookVariantFromDto(BookVariantDto bookVariantDto, @MappingTarget BookVariant bookVariant);
}
