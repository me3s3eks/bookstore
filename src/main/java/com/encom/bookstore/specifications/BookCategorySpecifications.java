package com.encom.bookstore.specifications;

import com.encom.bookstore.model.BookCategory;
import com.encom.bookstore.model.BookCategory_;
import org.springframework.data.jpa.domain.Specification;

public class BookCategorySpecifications {

    public static Specification<BookCategory> nameLike(String keyword) {
        return (root, query, cb) -> {
            String lowerCaseSearchTemplate = getLowerCaseSearchTemplate(keyword);
            return cb.like(cb.lower(root.get(BookCategory_.name)), lowerCaseSearchTemplate);
        };
    }

    private static String getLowerCaseSearchTemplate(String keyword) {
        return "%" + keyword.toLowerCase() + "%";
    }
}
