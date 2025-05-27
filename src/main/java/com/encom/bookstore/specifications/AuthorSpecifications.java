package com.encom.bookstore.specifications;

import com.encom.bookstore.model.Author;
import com.encom.bookstore.model.Author_;
import org.springframework.data.jpa.domain.Specification;

public class AuthorSpecifications {
    public static Specification<Author> nameLike(String keyword) {
        String lowerCaseSearchTemplate = getLowerCaseSearchTemplate(keyword);
        return (root, query, cb) -> {
            return cb.like(cb.lower(root.get(Author_.name)), lowerCaseSearchTemplate);
        };
    }

    public static Specification<Author> surnameLike(String keyword) {
        String lowerCaseSearchTemplate = getLowerCaseSearchTemplate(keyword);
        return (root, query, cb) -> {
            return cb.like(cb.lower(root.get(Author_.surname)), lowerCaseSearchTemplate);
        };
    }

    private static String getLowerCaseSearchTemplate(String keyword) {
        return "%" + keyword.toLowerCase() + "%";
    }
}
