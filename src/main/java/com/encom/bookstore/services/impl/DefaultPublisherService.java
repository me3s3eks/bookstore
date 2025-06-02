package com.encom.bookstore.services.impl;

import com.encom.bookstore.dto.PublisherBaseInfoDto;
import com.encom.bookstore.dto.PublisherDto;
import com.encom.bookstore.dto.PublisherRequestDto;
import com.encom.bookstore.exceptions.EntityNotFoundException;
import com.encom.bookstore.exceptions.ForeignKeyDeleteConstraintException;
import com.encom.bookstore.mappers.PublisherMapper;
import com.encom.bookstore.model.Country;
import com.encom.bookstore.model.Publisher;
import com.encom.bookstore.repositories.BookRepository;
import com.encom.bookstore.repositories.PublisherRepository;
import com.encom.bookstore.services.PublisherService;
import com.encom.bookstore.specifications.PublisherSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultPublisherService implements PublisherService {

    private final PublisherRepository publisherRepository;

    private final PublisherMapper publisherMapper;

    private final BookRepository bookRepository;

    @Override
    @Transactional
    public PublisherDto createPublisher(PublisherRequestDto publisherRequestDto) {
        Publisher publisher = publisherMapper.publisherRequestDtoToPublisher(publisherRequestDto);
        publisherRepository.save(publisher);
        return publisherMapper.publisherToPublisherDto(publisher);
    }

    @Override
    public PublisherDto findPublisher(long publisherId) {
        Publisher publisher = getPublisher(publisherId);
        return publisherMapper.publisherToPublisherDto(publisher);
    }

    @Override
    public Page<PublisherBaseInfoDto> findAllPublishers(Pageable pageable) {
        Page<Publisher> publishersPage = publisherRepository.findAll(pageable);
        return publishersPage.map(publisherMapper::publisherToPublisherBaseInfoDto);
    }

    @Override
    public Page<PublisherBaseInfoDto> findAllPublishersByParams(Pageable pageable, String keyword, List<Country> countries) {
        Specification<Publisher> publisherSpecification = getDynamicPublisherSpecification(keyword, countries);
        Page<Publisher> publishersPage = publisherRepository.findAll(publisherSpecification, pageable);
        return publishersPage.map(publisherMapper::publisherToPublisherBaseInfoDto);
    }

    @Override
    public Publisher getPublisher(long publisherId) {
        return publisherRepository.findById(publisherId)
            .orElseThrow(() -> new EntityNotFoundException("Publisher",
                Set.of(publisherId)));
    }

    @Override
    @Transactional
    public void deletePublisher(long publisherId) {
        if (bookRepository.existsByPublisherId(publisherId)) {
            throw new ForeignKeyDeleteConstraintException("Book");
        }
        Publisher publisher = getPublisher(publisherId);
        publisherRepository.delete(publisher);
    }

    @Override
    @Transactional
    public void updatePublisher(long publisherId, PublisherRequestDto publisherRequestDto) {
        Publisher updatedPublisher = getPublisher(publisherId);
        publisherMapper.updatePublisherFromDto(publisherRequestDto, updatedPublisher);
        publisherRepository.save(updatedPublisher);
    }

    private Specification<Publisher> getDynamicPublisherSpecification(String keyword, List<Country> countries) {
        return Specification.where(
          keyword != null ? PublisherSpecifications.nameLike(keyword).or(PublisherSpecifications.descriptionLike(keyword)) : null
        ).and(
            countries != null ? PublisherSpecifications.countryIn(countries) : null
        );
    }
}
