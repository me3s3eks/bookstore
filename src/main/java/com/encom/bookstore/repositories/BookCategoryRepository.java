package com.encom.bookstore.repositories;

import com.encom.bookstore.model.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Long>,
    JpaSpecificationExecutor<BookCategory> {

    boolean existsByParentId(long id);

    @Query(value = """
        WITH RECURSIVE category_subtree AS (
            SELECT id FROM book_categories WHERE id = :rootId
            UNION
            SELECT bc.id FROM book_categories bc
                INNER JOIN category_subtree cs ON bc.parent_id = cs.id
        )
        SELECT * FROM category_subtree
        """, nativeQuery = true)
    Set<Long> findIdsInSubtree(@Param("rootId") long rootId);
}
