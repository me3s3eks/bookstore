package com.encom.bookstore.repositories;

import com.encom.bookstore.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findWithRolesByLoginAndTimeOfRemovalNull(String login);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByLogin(String login);

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findWithRolesById(long userId);
}
