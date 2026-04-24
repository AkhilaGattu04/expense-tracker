package com.ExpenseTracker.repository;

import com.ExpenseTracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by email
     * @param email the email to search for
     * @return Optional containing user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if user exists by email
     * @param email the email to check
     * @return true if exists, false otherwise
     */
    boolean existsByEmail(String email);
}
