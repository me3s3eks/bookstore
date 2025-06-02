package com.encom.bookstore.services;

import com.encom.bookstore.dto.PublisherBaseInfoDto;
import com.encom.bookstore.dto.PublisherDto;
import com.encom.bookstore.dto.PublisherRequestDto;
import com.encom.bookstore.model.Country;
import com.encom.bookstore.model.Publisher;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PublisherService {

    PublisherDto createPublisher(PublisherRequestDto publisherRequestDto);

    PublisherDto findPublisher(long publisherId);

    Page<PublisherBaseInfoDto> findAllPublishers(Pageable pageable);

    Page<PublisherBaseInfoDto> findAllPublishersByParams(Pageable pageable, String keyword, List<Country> countries);

    Publisher getPublisher(long publisherId);

    void deletePublisher(long publisherId);

    void updatePublisher(long publisherId, PublisherRequestDto publisherRequestDto);
}
