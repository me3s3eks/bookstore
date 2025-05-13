package com.encom.bookstore.repositories;

import com.encom.bookstore.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("""
        SELECT a FROM Author a WHERE
        LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(a.surname) LIKE LOWER(CONCAT('%', :keyword, '%'))
        """)
    Page<Author> findAllByKeyword(Pageable pageable, String keyword);
}
