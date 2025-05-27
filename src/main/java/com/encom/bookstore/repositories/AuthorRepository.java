package com.encom.bookstore.repositories;

import com.encom.bookstore.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AuthorRepository extends JpaRepository<Author, Long>,
    JpaSpecificationExecutor<Author> {

}
