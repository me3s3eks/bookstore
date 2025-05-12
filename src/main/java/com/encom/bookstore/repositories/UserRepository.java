package com.encom.bookstore.repositories;

import com.encom.bookstore.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findWithRolesByLoginAndTimeOfRemovalNull(String login);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByLogin(String login);
}
