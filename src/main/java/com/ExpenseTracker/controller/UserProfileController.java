package com.ExpenseTracker.controller;

import com.ExpenseTracker.dto.UserProfileRequestDTO;
import com.ExpenseTracker.dto.UserProfileResponseDTO;
import com.ExpenseTracker.service.UserProfileService;
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

/**
 * REST Controller for UserProfile management operations.
 * Provides RESTful endpoints for managing user profile information.
 * Each user can have one profile containing contact details and preferences.
 * 
 * Base URL: /api/profiles
 * 
 * Responsibilities:
 * - Creating user profiles with contact information
 * - Retrieving profile information by user ID
 * - Updating profile details
 * - Deleting user profiles
 * 
 * All endpoints return appropriate HTTP status codes and error responses.
 * Request validation is performed using Jakarta Bean Validation.
 * 
 * @see UserProfileService for business logic implementation
 */
@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
@Tag(name = "User Profile Management", description = "APIs for managing user profiles")
public class UserProfileController {

    private final UserProfileService userProfileService;

    /**
     * Creates a new user profile.
     * Validates that the user exists and doesn't already have a profile.
     * 
     * HTTP Method: POST
     * Endpoint: /api/profiles
     * 
     * @param requestDTO the profile creation data containing userId, phone, address, and currency (validated)
     * @return ResponseEntity with created profile data and HTTP 201 (CREATED) status
     * @throws ResourceNotFoundException if user not found (returns HTTP 404)
     * @throws DuplicateResourceException if profile already exists for user (returns HTTP 409)
     * @throws MethodArgumentNotValidException if validation fails (returns HTTP 400)
     */
    @PostMapping
    @Operation(summary = "Create user profile", description = "Create a new profile for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profile created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "Profile already exists for this user")
    })
    public ResponseEntity<UserProfileResponseDTO> createProfile(
            @Valid @RequestBody UserProfileRequestDTO requestDTO) {
        // Delegate to service layer for profile creation
        UserProfileResponseDTO response = userProfileService.createProfile(requestDTO);
        // Return 201 Created with the new profile data
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieves a user's profile by their user ID.
     * 
     * HTTP Method: GET
     * Endpoint: /api/profiles/user/{userId}
     * 
     * @param userId the unique identifier of the user whose profile to retrieve (path variable)
     * @return ResponseEntity with profile data and HTTP 200 (OK) status
     * @throws ResourceNotFoundException if profile not found (returns HTTP 404)
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get profile by user ID", description = "Retrieve a user's profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile found"),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    public ResponseEntity<UserProfileResponseDTO> getProfileByUserId(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        // Fetch profile by user ID from service
        UserProfileResponseDTO response = userProfileService.getProfileByUserId(userId);
        // Return 200 OK with profile data
        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing user profile.
     * 
     * HTTP Method: PUT
     * Endpoint: /api/profiles/user/{userId}
     * 
     * @param userId the unique identifier of the user whose profile to update (path variable)
     * @param requestDTO the updated profile data containing phone, address, and currency (validated)
     * @return ResponseEntity with updated profile data and HTTP 200 (OK) status
     * @throws ResourceNotFoundException if profile not found (returns HTTP 404)
     * @throws MethodArgumentNotValidException if validation fails (returns HTTP 400)
     */
    @PutMapping("/user/{userId}")
    @Operation(summary = "Update user profile", description = "Update an existing user profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @ApiResponse(responseCode = "404", description = "Profile not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<UserProfileResponseDTO> updateProfile(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Valid @RequestBody UserProfileRequestDTO requestDTO) {
        // Update profile through service layer
        UserProfileResponseDTO response = userProfileService.updateProfile(userId, requestDTO);
        // Return 200 OK with updated profile data
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a user's profile.
     * 
     * HTTP Method: DELETE
     * Endpoint: /api/profiles/user/{userId}
     * 
     * @param userId the unique identifier of the user whose profile to delete (path variable)
     * @return ResponseEntity with no content and HTTP 204 (NO_CONTENT) status
     * @throws ResourceNotFoundException if profile not found (returns HTTP 404)
     */
    @DeleteMapping("/user/{userId}")
    @Operation(summary = "Delete user profile", description = "Delete a user's profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Profile deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    public ResponseEntity<Void> deleteProfile(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        // Delete profile through service layer
        userProfileService.deleteProfile(userId);
        // Return 204 No Content (successful deletion)
        return ResponseEntity.noContent().build();
    }
}
