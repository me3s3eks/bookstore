package com.encom.bookstore.repositories;

import com.encom.bookstore.model.Book;
import com.encom.bookstore.model.BookVariant;
import com.encom.bookstore.model.BookVariantId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookVariantRepository extends JpaRepository<BookVariant, BookVariantId> {

    @EntityGraph(value = "BookVariant.paperBookProperties")
    Optional<BookVariant> findWithPropertiesById(BookVariantId bookVariantId);

    @EntityGraph(value = "BookVariant.paperBookProperties")
    List<BookVariant> findWithPropertiesByBook(Book book);

    @EntityGraph(value = "BookVariant.paperBookProperties")
    Optional<BookVariant> findWithPaperBookPropertiesById(BookVariantId bookVariantId);
}
