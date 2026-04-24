package com.ExpenseTracker.service.impl;

import com.ExpenseTracker.dto.UserRequestDTO;
import com.ExpenseTracker.dto.UserResponseDTO;
import com.ExpenseTracker.entity.User;
import com.ExpenseTracker.exception.DuplicateResourceException;
import com.ExpenseTracker.exception.ResourceNotFoundException;
import com.ExpenseTracker.repository.UserRepository;
import com.ExpenseTracker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the UserService interface.
 * Handles all business logic related to user management including CRUD operations.
 * Uses Spring Data JPA for database operations and implements transaction management.
 * 
 * @see UserService
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Creates a new user in the system.
     * Validates email uniqueness before persisting the user to the database.
     * 
     * @param requestDTO the user creation data containing name, email, and password
     * @return the created user response with generated ID and timestamps
     * @throws DuplicateResourceException if a user with the given email already exists
     */
    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        log.info("Creating user with email: {}", requestDTO.getEmail());

        // Validate email uniqueness - ensure no duplicate emails in the system
        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("User", "email", requestDTO.getEmail());
        }

        // Convert DTO to entity and persist to database
        User user = mapToEntity(requestDTO);
        User savedUser = userRepository.save(user);

        log.info("Successfully created user with ID: {}", savedUser.getId());
        return mapToResponseDTO(savedUser);
    }

    /**
     * Retrieves all users from the database.
     * Uses read-only transaction for optimized performance.
     * 
     * @return a list of all users as response DTOs, empty list if no users exist
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        log.info("Fetching all users");
        
        // Fetch all users and transform to DTOs using Java streams
        return userRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific user by their unique identifier.
     * Uses read-only transaction for optimized performance.
     * 
     * @param id the unique identifier of the user to retrieve
     * @return the user response DTO containing user details
     * @throws ResourceNotFoundException if no user exists with the given ID
     */
    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        log.info("Fetching user with ID: {}", id);
        
        // Find user by ID or throw exception if not found
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return mapToResponseDTO(user);
    }

    /**
     * Updates an existing user's information.
     * Validates that the new email (if changed) is not already in use by another user.
     * 
     * @param id the unique identifier of the user to update
     * @param requestDTO the updated user data containing name, email, and password
     * @return the updated user response DTO with modified timestamps
     * @throws ResourceNotFoundException if no user exists with the given ID
     * @throws DuplicateResourceException if the new email already exists for another user
     */
    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO) {
        log.info("Updating user with ID: {}", id);

        // Verify user exists before attempting update
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        // Validate email uniqueness if email is being changed
        // Only check if the new email is different from current email
        if (!user.getEmail().equals(requestDTO.getEmail()) && 
            userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("User", "email", requestDTO.getEmail());
        }

        // Update user fields with new values
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());

        // Persist changes to database
        User updatedUser = userRepository.save(user);
        log.info("Successfully updated user with ID: {}", id);
        return mapToResponseDTO(updatedUser);
    }

    /**
     * Deletes a user from the system.
     * Verifies user existence before attempting deletion.
     * 
     * @param id the unique identifier of the user to delete
     * @throws ResourceNotFoundException if no user exists with the given ID
     */
    @Override
    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);

        // Verify user exists before deletion
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", "id", id);
        }

        // Remove user from database
        userRepository.deleteById(id);
        log.info("Successfully deleted user with ID: {}", id);
    }

    /**
     * Maps a UserRequestDTO to a User entity.
     * Helper method for converting request data transfer objects to domain entities.
     * 
     * @param dto the user request DTO containing user creation/update data
     * @return a new User entity populated with data from the DTO
     */
    private User mapToEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    /**
     * Maps a User entity to a UserResponseDTO.
     * Helper method for converting domain entities to response data transfer objects.
     * Excludes sensitive information like passwords from the response.
     * 
     * @param user the User entity to convert
     * @return a UserResponseDTO containing user information suitable for API responses
     */
    private UserResponseDTO mapToResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
