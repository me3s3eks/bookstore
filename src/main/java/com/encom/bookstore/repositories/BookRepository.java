package com.encom.bookstore.repositories;

import com.encom.bookstore.model.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>,
    JpaSpecificationExecutor<Book>, CustomBookRepository {

    @EntityGraph(attributePaths = {"authors"})
    List<Book> findWithAuthorsByIdIn(List<Long> ids);

    @EntityGraph(attributePaths = {"authors", "bookCategory", "publisher"})
    Optional<Book> findWithAllRelatedEntitiesById(long id);

    boolean existsByAuthorsId(long id);

    boolean existsByBookCategoryId(long id);

    boolean existsByPublisherId(long id);
}
