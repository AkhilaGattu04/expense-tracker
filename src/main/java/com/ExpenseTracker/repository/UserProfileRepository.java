package com.ExpenseTracker.repository;

import com.ExpenseTracker.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for UserProfile entity
 */
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    /**
     * Find user profile by user ID
     * @param userId the user ID
     * @return Optional containing user profile if found
     */
    Optional<UserProfile> findByUserId(Long userId);

    /**
     * Check if profile exists for user ID
     * @param userId the user ID
     * @return true if exists, false otherwise
     */
    boolean existsByUserId(Long userId);

    /**
     * Delete profile by user ID
     * @param userId the user ID
     */
    void deleteByUserId(Long userId);
}
