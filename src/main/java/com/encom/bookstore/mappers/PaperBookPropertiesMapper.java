package com.encom.bookstore.mappers;

import com.encom.bookstore.dto.PaperBookPropertiesDto;
import com.encom.bookstore.model.PaperBookProperties;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaperBookPropertiesMapper {

    PaperBookProperties toPaperBookProperties(PaperBookPropertiesDto paperBookPropertiesDto);

    PaperBookPropertiesDto paperBookPropertiesToDto(PaperBookProperties paperBookProperties);

    PaperBookProperties updatePaperBookProperties(PaperBookPropertiesDto paperBookPropertiesDto,
                                                  @MappingTarget PaperBookProperties paperBookProperties);
}
