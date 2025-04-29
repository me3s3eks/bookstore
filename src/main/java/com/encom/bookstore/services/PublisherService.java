package com.encom.bookstore.services;

import com.encom.bookstore.dto.PublisherBaseInfoDto;
import com.encom.bookstore.dto.PublisherDto;
import com.encom.bookstore.model.Publisher;

public interface PublisherService {

    PublisherDto findPublisher(long publisherId);

    PublisherBaseInfoDto findPublisherBaseInfo(long publisherId);

    Publisher getPublisher(long publisherId);
}
