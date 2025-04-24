package com.encom.bookstore.repositories;

import com.encom.bookstore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCatalogueRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b.id FROM Book b")
    Page<Long> findAllIds (Pageable pageable);

    @EntityGraph(attributePaths = {"authors"})
//    @Query("SELECT b FROM Book b WHERE b.id IN :ids")
//    @Param("ids")
    List<Book> findAllWithAuthorsByIdIn(List<Long> ids);
}


