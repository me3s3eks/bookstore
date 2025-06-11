package com.encom.bookstore.repositories.impl;

import com.encom.bookstore.model.Book;
import com.encom.bookstore.model.Book_;
import com.encom.bookstore.repositories.CustomBookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CustomBookRepositoryImpl implements CustomBookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Page<Long> findIdsBySpecification(Specification<Book> specification, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Book> root = query.from(Book.class);

        query.select(root.get(Book_.id));
        Predicate predicate = specification.toPredicate(root, query, cb);
        if (predicate == null) {
            predicate = cb.conjunction();
        }
        query.where(predicate);

        TypedQuery<Long> idsQuery = entityManager.createQuery(query);
        idsQuery.setFirstResult((int) pageable.getOffset());
        idsQuery.setMaxResults(pageable.getPageSize());
        List<Long> bookIds = idsQuery.getResultList();

        query.select(cb.countDistinct(root));
        TypedQuery<Long> countQuery = entityManager.createQuery(query);
        long totalEntities = countQuery.getSingleResult();

        return new PageImpl<>(bookIds, pageable, totalEntities);
    }
}
