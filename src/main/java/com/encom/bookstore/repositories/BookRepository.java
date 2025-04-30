package com.encom.bookstore.repositories;

import com.encom.bookstore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b.id FROM Book b")
    Page<Long> findAllIds(Pageable pageable);

    @Query("SELECT b.id FROM Book b WHERE b.timeOfRemoval IS NULL")
    Page<Long> findAllActiveIds (Pageable pageable);

    @EntityGraph(attributePaths = {"authors"})
    List<Book> findAllWithAuthorsByIdIn(List<Long> ids);

    @EntityGraph(attributePaths = {"authors", "bookCategory", "publisher"})
    Optional<Book> findWithAllRelatedEntitiesById(long id);

    boolean existsByAuthorsId(long id);
}
