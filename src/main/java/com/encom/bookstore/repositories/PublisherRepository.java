package com.encom.bookstore.repositories;

import com.encom.bookstore.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PublisherRepository extends JpaRepository<Publisher, Long>,
    JpaSpecificationExecutor<Publisher> {
}
