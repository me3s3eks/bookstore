package com.encom.bookstore.services.impl;

import com.encom.bookstore.dto.PublisherBaseInfoDto;
import com.encom.bookstore.dto.PublisherDto;
import com.encom.bookstore.exceptions.EntityNotFoundException;
import com.encom.bookstore.mappers.PublisherMapper;
import com.encom.bookstore.model.Publisher;
import com.encom.bookstore.repositories.PublisherRepository;
import com.encom.bookstore.services.PublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultPublisherService implements PublisherService {

    private final PublisherRepository publisherRepository;

    private final PublisherMapper publisherMapper;

    @Override
    public PublisherDto findPublisher(long publisherId) {
        Publisher publisher = getPublisher(publisherId);
        return publisherMapper.publisherToPublisherDto(publisher);
    }

    @Override
    public PublisherBaseInfoDto findPublisherBaseInfo(long publisherId) {
        Publisher publisher = getPublisher(publisherId);
        return publisherMapper.publisherToPublisherBaseInfoDto(publisher);
    }

    @Override
    public Publisher getPublisher(long publisherId) {
        return publisherRepository.findById(publisherId)
            .orElseThrow(() -> new EntityNotFoundException("Publisher",
                Set.of(publisherId)));
    }

}
