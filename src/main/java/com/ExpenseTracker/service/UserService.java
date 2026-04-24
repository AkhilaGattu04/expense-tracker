package com.ExpenseTracker.service;

import com.ExpenseTracker.dto.UserRequestDTO;
import com.ExpenseTracker.dto.UserResponseDTO;

import java.util.List;

/**
 * Service interface for User operations.
 * Provides business logic for managing user entities in the expense tracker system.
 */
public interface UserService {

    /**
     * Creates a new user in the system.
     * Validates that the email is unique before creating the user.
     * 
     * @param requestDTO the user creation data containing name, email, and password
     * @return the created user response with generated ID and timestamps
     * @throws DuplicateResourceException if a user with the given email already exists
     */
    UserResponseDTO createUser(UserRequestDTO requestDTO);

    /**
     * Retrieves all users from the system.
     * 
     * @return a list of all users as response DTOs, empty list if no users exist
     */
    List<UserResponseDTO> getAllUsers();

    /**
     * Retrieves a specific user by their unique identifier.
     * 
     * @param id the unique identifier of the user to retrieve
     * @return the user response DTO containing user details
     * @throws ResourceNotFoundException if no user exists with the given ID
     */
    UserResponseDTO getUserById(Long id);

    /**
     * Updates an existing user's information.
     * Validates that the new email (if changed) is unique before updating.
     * 
     * @param id the unique identifier of the user to update
     * @param requestDTO the updated user data containing name, email, and password
     * @return the updated user response DTO with modified timestamps
     * @throws ResourceNotFoundException if no user exists with the given ID
     * @throws DuplicateResourceException if the new email already exists for another user
     */
    UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO);

    /**
     * Deletes a user from the system.
     * 
     * @param id the unique identifier of the user to delete
     * @throws ResourceNotFoundException if no user exists with the given ID
     */
    void deleteUser(Long id);
}
