package com.encom.bookstore.repositories;

import com.encom.bookstore.model.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = {"authors", "bookCategory", "publisher"})
    Optional<Book> findWithAllRelatedEntitiesById(long id);
}
