package com.encom.bookstore.specifications;

import com.encom.bookstore.model.Role;
import com.encom.bookstore.model.Role_;
import com.encom.bookstore.model.User;
import com.encom.bookstore.model.User_;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.SetJoin;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Set;

public class UserSpecifications {

    public static Specification<User> loginLike(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) -> {
            String lowerCaseSearchTemplate = getLowerCaseSearchTemplate(keyword);
            return cb.like(cb.lower(root.get(User_.login)), lowerCaseSearchTemplate);
        };
    }

    public static Specification<User> emailLike(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) -> {
            String lowerCaseSearchTemplate = getLowerCaseSearchTemplate(keyword);
            return cb.like(cb.lower(root.get(User_.email)), lowerCaseSearchTemplate);
        };
    }

    public static Specification<User> dateOfBirthOnOrAfter(LocalDate localDate) {
        if (localDate == null) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) -> {
            return cb.greaterThanOrEqualTo(root.get(User_.dateOfBirth), localDate);
        };
    }

    public static Specification<User> dateOfBirthOnOrBefore(LocalDate localDate) {
        if (localDate == null) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) -> {
            return cb.lessThanOrEqualTo(root.get(User_.dateOfBirth), localDate);
        };
    }

    public static Specification<User> isDeleted(Boolean deleted) {
        if (deleted == null) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) -> {
            if (deleted) {
                return cb.isNotNull(root.get(User_.timeOfRemoval));
            } else {
                return cb.isNull(root.get(User_.timeOfRemoval));
            }
        };
    }

    public static Specification<User> withRoleIdIn(Set<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return (root, query, cb) -> cb. conjunction();
        }

        return (root, query, cb) -> {
            if (query.getResultType() == Long.class) {
                Subquery<Long> subQuery = query.subquery(Long.class);
                Root<User> subRoot = subQuery.from(User.class);
                Join<User, Role> subJoin = subRoot.join(User_.roles);
                subQuery.select(subRoot.get(User_.id))
                    .where(subJoin.get(Role_.id).in(roleIds));
                return cb.in(root.get(User_.id)).value(subQuery);
            }
            query.distinct(true);
            SetJoin<User, Role> userRoleJoin = root.join(User_.roles, JoinType.LEFT);
            return userRoleJoin.get(Role_.id).in(roleIds);
        };
    }

    private static String getLowerCaseSearchTemplate(String keyword) {
        return "%" + keyword.toLowerCase() + "%";
    }
}
