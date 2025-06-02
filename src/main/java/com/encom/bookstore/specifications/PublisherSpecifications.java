package com.encom.bookstore.specifications;

import com.encom.bookstore.model.Country;
import com.encom.bookstore.model.Publisher;
import com.encom.bookstore.model.Publisher_;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class PublisherSpecifications {

    public static Specification<Publisher> nameLike(String keyword) {
        return (root, query, cb) -> {
            String lowerCaseSearchTemplate = getLowerCaseSearchTemplate(keyword);
            return cb.like(cb.lower(root.get(Publisher_.name)), lowerCaseSearchTemplate);
        };
    }

    public static Specification<Publisher> descriptionLike(String keyword) {
        return (root, query, cb) -> {
            String lowerCaseSearchTemplate = getLowerCaseSearchTemplate(keyword);
            return cb.like(cb.lower(root.get(Publisher_.description)), lowerCaseSearchTemplate);
        };
    }

    public static Specification<Publisher> countryIn(List<Country> countries) {
        return (root, query, cb) -> {
            return root.get(Publisher_.country).in(countries);
        };
    }

    private static String getLowerCaseSearchTemplate(String keyword) {
        return "%" + keyword.toLowerCase() + "%";
    }
}
