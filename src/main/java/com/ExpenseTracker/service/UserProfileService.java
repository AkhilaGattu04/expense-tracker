package com.ExpenseTracker.service;

import com.ExpenseTracker.dto.UserProfileRequestDTO;
import com.ExpenseTracker.dto.UserProfileResponseDTO;

/**
 * Service interface for UserProfile operations.
 * Provides business logic for managing user profile information including phone, address, and currency preferences.
 * Each user can have only one profile (one-to-one relationship).
 */
public interface UserProfileService {

    /**
     * Creates a new user profile.
     * Validates that the user exists and doesn't already have a profile before creation.
     * 
     * @param requestDTO the profile creation data containing userId, phone, address, and currency
     * @return the created user profile response with generated ID
     * @throws ResourceNotFoundException if the user with the given userId doesn't exist
     * @throws DuplicateResourceException if a profile already exists for the user
     */
    UserProfileResponseDTO createProfile(UserProfileRequestDTO requestDTO);

    /**
     * Retrieves a user's profile by their user ID.
     * 
     * @param userId the unique identifier of the user whose profile to retrieve
     * @return the user profile response DTO
     * @throws ResourceNotFoundException if no profile exists for the given userId
     */
    UserProfileResponseDTO getProfileByUserId(Long userId);

    /**
     * Updates an existing user profile.
     * 
     * @param userId the unique identifier of the user whose profile to update
     * @param requestDTO the updated profile data containing phone, address, and currency
     * @return the updated user profile response DTO
     * @throws ResourceNotFoundException if no profile exists for the given userId
     */
    UserProfileResponseDTO updateProfile(Long userId, UserProfileRequestDTO requestDTO);

    /**
     * Deletes a user's profile.
     * 
     * @param userId the unique identifier of the user whose profile to delete
     * @throws ResourceNotFoundException if no profile exists for the given userId
     */
    void deleteProfile(Long userId);
}
