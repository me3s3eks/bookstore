package com.encom.bookstore.repositories;

import com.encom.bookstore.model.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Long>,
    JpaSpecificationExecutor<BookCategory> {

    boolean existsByParentId(long id);
}
