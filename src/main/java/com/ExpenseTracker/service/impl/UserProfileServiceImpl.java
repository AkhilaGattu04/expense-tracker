package com.ExpenseTracker.service.impl;

import com.ExpenseTracker.dto.UserProfileRequestDTO;
import com.ExpenseTracker.dto.UserProfileResponseDTO;
import com.ExpenseTracker.entity.User;
import com.ExpenseTracker.entity.UserProfile;
import com.ExpenseTracker.exception.DuplicateResourceException;
import com.ExpenseTracker.exception.ResourceNotFoundException;
import com.ExpenseTracker.repository.UserProfileRepository;
import com.ExpenseTracker.repository.UserRepository;
import com.ExpenseTracker.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the UserProfileService interface.
 * Handles business logic for managing user profiles with their contact information and preferences.
 * Enforces one-to-one relationship between users and profiles.
 * Uses Spring Data JPA for database operations and implements transaction management.
 * 
 * @see UserProfileService
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    /**
     * Creates a new user profile.
     * Validates user existence and ensures no duplicate profiles are created.
     * 
     * @param requestDTO the profile creation data containing userId, phone, address, and currency
     * @return the created user profile response with generated ID
     * @throws ResourceNotFoundException if the user with the given userId doesn't exist
     * @throws DuplicateResourceException if a profile already exists for the user
     */
    @Override
    public UserProfileResponseDTO createProfile(UserProfileRequestDTO requestDTO) {
        log.info("Creating profile for user ID: {}", requestDTO.getUserId());

        // Validate that the user exists before creating profile
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", requestDTO.getUserId()));

        // Enforce one-to-one relationship - check if profile already exists for this user
        if (userProfileRepository.existsByUserId(requestDTO.getUserId())) {
            throw new DuplicateResourceException("User profile already exists for user ID: " + requestDTO.getUserId());
        }

        // Create and persist the new profile
        UserProfile profile = mapToEntity(requestDTO, user);
        UserProfile savedProfile = userProfileRepository.save(profile);

        log.info("Successfully created profile with ID: {}", savedProfile.getId());
        return mapToResponseDTO(savedProfile);
    }

    /**
     * Retrieves a user's profile by their user ID.
     * Uses read-only transaction for optimized performance.
     * 
     * @param userId the unique identifier of the user whose profile to retrieve
     * @return the user profile response DTO
     * @throws ResourceNotFoundException if no profile exists for the given userId
     */
    @Override
    @Transactional(readOnly = true)
    public UserProfileResponseDTO getProfileByUserId(Long userId) {
        log.info("Fetching profile for user ID: {}", userId);
        
        // Find profile by userId or throw exception if not found
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Profile", "userId", userId));
        
        return mapToResponseDTO(profile);
    }

    /**
     * Updates an existing user profile.
     * 
     * @param userId the unique identifier of the user whose profile to update
     * @param requestDTO the updated profile data containing phone, address, and currency
     * @return the updated user profile response DTO
     * @throws ResourceNotFoundException if no profile exists for the given userId
     */
    @Override
    public UserProfileResponseDTO updateProfile(Long userId, UserProfileRequestDTO requestDTO) {
        log.info("Updating profile for user ID: {}", userId);

        // Verify profile exists before attempting update
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Profile", "userId", userId));

        // Update profile fields with new values
        profile.setPhone(requestDTO.getPhone());
        profile.setAddress(requestDTO.getAddress());
        profile.setCurrency(requestDTO.getCurrency());

        // Persist changes to database
        UserProfile updatedProfile = userProfileRepository.save(profile);
        log.info("Successfully updated profile for user ID: {}", userId);
        return mapToResponseDTO(updatedProfile);
    }

    /**
     * Deletes a user's profile.
     * Verifies profile existence before attempting deletion.
     * 
     * @param userId the unique identifier of the user whose profile to delete
     * @throws ResourceNotFoundException if no profile exists for the given userId
     */
    @Override
    public void deleteProfile(Long userId) {
        log.info("Deleting profile for user ID: {}", userId);

        // Verify profile exists before deletion
        if (!userProfileRepository.existsByUserId(userId)) {
            throw new ResourceNotFoundException("User Profile", "userId", userId);
        }

        // Remove profile from database
        userProfileRepository.deleteByUserId(userId);
        log.info("Successfully deleted profile for user ID: {}", userId);
    }

    /**
     * Maps a UserProfileRequestDTO to a UserProfile entity.
     * Helper method for converting request data transfer objects to domain entities.
     * Associates the profile with the provided user entity.
     * 
     * @param dto the user profile request DTO containing profile data
     * @param user the User entity to associate with this profile
     * @return a new UserProfile entity populated with data from the DTO
     */
    private UserProfile mapToEntity(UserProfileRequestDTO dto, User user) {
        UserProfile profile = new UserProfile();
        profile.setPhone(dto.getPhone());
        profile.setAddress(dto.getAddress());
        profile.setCurrency(dto.getCurrency());
        profile.setUser(user);
        return profile;
    }

    /**
     * Maps a UserProfile entity to a UserProfileResponseDTO.
     * Helper method for converting domain entities to response data transfer objects.
     * Includes the associated userId for API responses.
     * 
     * @param profile the UserProfile entity to convert
     * @return a UserProfileResponseDTO containing profile information suitable for API responses
     */
    private UserProfileResponseDTO mapToResponseDTO(UserProfile profile) {
        return new UserProfileResponseDTO(
                profile.getId(),
                profile.getPhone(),
                profile.getAddress(),
                profile.getCurrency(),
                profile.getUser().getId()
        );
    }
}
