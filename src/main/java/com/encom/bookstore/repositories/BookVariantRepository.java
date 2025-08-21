package com.encom.bookstore.repositories;

import com.encom.bookstore.model.Book;
import com.encom.bookstore.model.BookVariant;
import com.encom.bookstore.model.BookVariantId;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

public interface BookVariantRepository extends JpaRepository<BookVariant, BookVariantId> {

    @EntityGraph(value = "BookVariant.paperBookProperties")
    Optional<BookVariant> findWithPropertiesById(BookVariantId bookVariantId);

    @EntityGraph(value = "BookVariant.paperBookProperties")
    List<BookVariant> findWithPropertiesByBook(Book book);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(value = "BookVariant.paperBookProperties")
    Optional<BookVariant> findLockWithPropertiesById(BookVariantId bookVariantId);
}
