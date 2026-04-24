package com.ExpenseTracker.controller;

import com.ExpenseTracker.dto.UserRequestDTO;
import com.ExpenseTracker.dto.UserResponseDTO;
import com.ExpenseTracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for User management operations.
 * Provides RESTful endpoints for managing user accounts in the expense tracker system.
 * 
 * Base URL: /api/users
 * 
 * Responsibilities:
 * - User registration and account creation
 * - Retrieving user information (single or all)
 * - Updating user details
 * - Deleting user accounts
 * 
 * All endpoints return appropriate HTTP status codes and error responses.
 * Request validation is performed using Jakarta Bean Validation.
 * 
 * @see UserService for business logic implementation
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    private final UserService userService;

    /**
     * Creates a new user account.
     * Validates input data and ensures email uniqueness.
     * 
     * HTTP Method: POST
     * Endpoint: /api/users
     * 
     * @param requestDTO the user creation data containing name, email, and password (validated)
     * @return ResponseEntity with created user data and HTTP 201 (CREATED) status
     * @throws DuplicateResourceException if email already exists (returns HTTP 409)
     * @throws MethodArgumentNotValidException if validation fails (returns HTTP 400)
     */
    @PostMapping
    @Operation(summary = "Create a new user", description = "Register a new user in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        // Delegate to service layer for business logic
        UserResponseDTO response = userService.createUser(requestDTO);
        // Return 201 Created with the new user data
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieves all users in the system.
     * 
     * HTTP Method: GET
     * Endpoint: /api/users
     * 
     * @return ResponseEntity with list of all users and HTTP 200 (OK) status
     */
    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        // Fetch all users from service
        List<UserResponseDTO> users = userService.getAllUsers();
        // Return 200 OK with user list
        return ResponseEntity.ok(users);
    }

    /**
     * Retrieves a specific user by their unique identifier.
     * 
     * HTTP Method: GET
     * Endpoint: /api/users/{id}
     * 
     * @param id the unique identifier of the user to retrieve (path variable)
     * @return ResponseEntity with user data and HTTP 200 (OK) status
     * @throws ResourceNotFoundException if user not found (returns HTTP 404)
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a specific user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponseDTO> getUserById(
            @Parameter(description = "ID of the user to retrieve") @PathVariable Long id) {
        // Fetch user by ID from service
        UserResponseDTO response = userService.getUserById(id);
        // Return 200 OK with user data
        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing user's information.
     * Validates input and ensures email uniqueness if email is changed.
     * 
     * HTTP Method: PUT
     * Endpoint: /api/users/{id}
     * 
     * @param id the unique identifier of the user to update (path variable)
     * @param requestDTO the updated user data containing name, email, and password (validated)
     * @return ResponseEntity with updated user data and HTTP 200 (OK) status
     * @throws ResourceNotFoundException if user not found (returns HTTP 404)
     * @throws DuplicateResourceException if new email already exists (returns HTTP 409)
     * @throws MethodArgumentNotValidException if validation fails (returns HTTP 400)
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Update an existing user's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    public ResponseEntity<UserResponseDTO> updateUser(
            @Parameter(description = "ID of the user to update") @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO requestDTO) {
        // Update user through service layer
        UserResponseDTO response = userService.updateUser(id, requestDTO);
        // Return 200 OK with updated user data
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a user account from the system.
     * 
     * HTTP Method: DELETE
     * Endpoint: /api/users/{id}
     * 
     * @param id the unique identifier of the user to delete (path variable)
     * @return ResponseEntity with no content and HTTP 204 (NO_CONTENT) status
     * @throws ResourceNotFoundException if user not found (returns HTTP 404)
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Delete a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user to delete") @PathVariable Long id) {
        // Delete user through service layer
        userService.deleteUser(id);
        // Return 204 No Content (successful deletion)
        return ResponseEntity.noContent().build();
    }
}
