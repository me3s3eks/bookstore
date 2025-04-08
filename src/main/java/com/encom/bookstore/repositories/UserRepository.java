package com.encom.bookstore.repositories;

import com.encom.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginIgnoreCaseAndTimeOfRemovalNotNull(String login);

    /*@Modifying
    void updatePassword(String password);

    @Modifying
    void updateTimeOfRemoval(LocalDateTime timeOfRemoval);*/
}
