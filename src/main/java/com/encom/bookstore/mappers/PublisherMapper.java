package com.encom.bookstore.mappers;

import com.encom.bookstore.dto.PublisherBaseInfoDto;
import com.encom.bookstore.model.Publisher;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PublisherMapper {
    PublisherBaseInfoDto publisherToPublisherBaseInfoDto(Publisher publisher);
}
