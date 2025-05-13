package com.encom.bookstore.repositories;

import com.encom.bookstore.model.BookCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {

    @Query("""
        SELECT bc FROM BookCategory bc WHERE
        LOWER(bc.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        """)
    Page<BookCategory> findAllByKeyword(Pageable pageable, String keyword);
}
