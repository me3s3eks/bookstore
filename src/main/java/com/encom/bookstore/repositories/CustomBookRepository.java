package com.encom.bookstore.repositories;

import com.encom.bookstore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CustomBookRepository {

    Page<Long> findIdsBySpecification(Specification<Book> specification, Pageable pageable);
}
