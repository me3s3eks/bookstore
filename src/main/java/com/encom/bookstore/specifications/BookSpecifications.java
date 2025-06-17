package com.encom.bookstore.specifications;

import com.encom.bookstore.model.Author;
import com.encom.bookstore.model.Author_;
import com.encom.bookstore.model.Book;
import com.encom.bookstore.model.BookCategory;
import com.encom.bookstore.model.BookCategory_;
import com.encom.bookstore.model.Book_;
import com.encom.bookstore.model.Language;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class BookSpecifications {

    public static Specification<Book> titleLike(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) -> {
            String lowerCaseSearchTemplate = getLowerCaseSearchTemplate(keyword);
            return cb.like(cb.lower(root.get(Book_.title)), lowerCaseSearchTemplate);
        };
    }

    public static Specification<Book> byIsbn(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) -> {
            return cb.equal(cb.upper(root.get(Book_.isbn)), isbn);
        };
    }

    public static Specification<Book> withAuthorNameOrSurnameLike(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) -> {
            query.distinct(true);
            Join<Book, Author> authorsJoin = root.join(Book_.authors);
            String lowerCaseSearchTemplate = getLowerCaseSearchTemplate(keyword);

            Predicate authorNameLikePredicate = cb.like(cb.lower(authorsJoin.get(Author_.name)), lowerCaseSearchTemplate);
            Predicate authorSurnameLikePredicate = cb.like(cb.lower(authorsJoin.get(Author_.surname)), lowerCaseSearchTemplate);
            return cb.or(authorNameLikePredicate, authorSurnameLikePredicate);
        };
    }

    public static Specification<Book> withAuthorId(long authorId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<Book, Author> authorsJoin = root.join(Book_.authors);
            return cb.equal(authorsJoin.get(Author_.id), authorId);
        };
    }

    public static Specification<Book> withAuthor(long authorId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<Book, Author>  authorsJoin = root.join(Book_.authors);
            return cb.equal(authorsJoin.get(Author_.id), authorId);
        };
    }

    public static Specification<Book> withBookCategoriesIn(Set<Long> bookCategoryIds) {
        if (bookCategoryIds == null || bookCategoryIds.isEmpty()) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) -> {
            Join<Book, BookCategory> bookCategoriesJoin = root.join(Book_.bookCategory);
            return bookCategoriesJoin.get(BookCategory_.id).in(bookCategoryIds);
        };
    }

    public static Specification<Book> publicationDateOnOrAfter(LocalDate publicationDate) {
        if (publicationDate == null) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) -> {
            return cb.greaterThanOrEqualTo(root.get(Book_.publicationDate), publicationDate);
        };
    }

    public static Specification<Book> languageIn(List<Language> languages) {
        if (languages == null || languages.isEmpty()) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) -> {
            return root.get(Book_.language).in(languages);
        };
    }

    public static Specification<Book> isDeleted(Boolean deleted) {
        if (deleted == null) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) -> {
            if (deleted) {
                return cb.isNotNull(root.get(Book_.timeOfRemoval));
            } else {
                return cb.isNull(root.get(Book_.timeOfRemoval));
            }
        };
    }

    private static String getLowerCaseSearchTemplate(String keyword) {
        return "%" + keyword.toLowerCase() + "%";
    }
}
