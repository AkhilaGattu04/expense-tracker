package com.ExpenseTracker.controller;

import com.ExpenseTracker.dto.ExpenseCategoryRequestDTO;
import com.ExpenseTracker.dto.ExpenseCategoryResponseDTO;
import com.ExpenseTracker.service.ExpenseCategoryService;
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
 * REST Controller for ExpenseCategory management operations.
 * Provides RESTful endpoints for managing expense categories used to classify expenses.
 * Categories help organize expenses into logical groups (e.g., Food, Transport, Entertainment).
 * 
 * Base URL: /api/categories
 * 
 * Responsibilities:
 * - Creating new expense categories
 * - Retrieving category information (single or all)
 * - Updating category details
 * - Deleting expense categories
 * 
 * All endpoints return appropriate HTTP status codes and error responses.
 * Request validation is performed using Jakarta Bean Validation.
 * 
 * @see ExpenseCategoryService for business logic implementation
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Expense Category Management", description = "APIs for managing expense categories")
public class ExpenseCategoryController {

    private final ExpenseCategoryService categoryService;

    /**
     * Creates a new expense category.
     * Validates input data and ensures category name uniqueness.
     * 
     * HTTP Method: POST
     * Endpoint: /api/categories
     * 
     * @param requestDTO the category creation data containing name and description (validated)
     * @return ResponseEntity with created category data and HTTP 201 (CREATED) status
     * @throws DuplicateResourceException if category name already exists (returns HTTP 409)
     * @throws MethodArgumentNotValidException if validation fails (returns HTTP 400)
     */
    @PostMapping
    @Operation(summary = "Create expense category", description = "Create a new expense category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Category name already exists")
    })
    public ResponseEntity<ExpenseCategoryResponseDTO> createCategory(
            @Valid @RequestBody ExpenseCategoryRequestDTO requestDTO) {
        // Delegate to service layer for category creation
        ExpenseCategoryResponseDTO response = categoryService.createCategory(requestDTO);
        // Return 201 Created with the new category data
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieves all expense categories in the system.
     * 
     * HTTP Method: GET
     * Endpoint: /api/categories
     * 
     * @return ResponseEntity with list of all categories and HTTP 200 (OK) status
     */
    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieve all expense categories")
    @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    public ResponseEntity<List<ExpenseCategoryResponseDTO>> getAllCategories() {
        // Fetch all categories from service
        List<ExpenseCategoryResponseDTO> categories = categoryService.getAllCategories();
        // Return 200 OK with category list
        return ResponseEntity.ok(categories);
    }

    /**
     * Retrieves a specific expense category by its unique identifier.
     * 
     * HTTP Method: GET
     * Endpoint: /api/categories/{id}
     * 
     * @param id the unique identifier of the category to retrieve (path variable)
     * @return ResponseEntity with category data and HTTP 200 (OK) status
     * @throws ResourceNotFoundException if category not found (returns HTTP 404)
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID", description = "Retrieve a specific category by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<ExpenseCategoryResponseDTO> getCategoryById(
            @Parameter(description = "ID of the category to retrieve") @PathVariable Long id) {
        // Fetch category by ID from service
        ExpenseCategoryResponseDTO response = categoryService.getCategoryById(id);
        // Return 200 OK with category data
        return ResponseEntity.ok(response);
    }

    /**
     * Updates an existing expense category.
     * Validates input and ensures category name uniqueness if name is changed.
     * 
     * HTTP Method: PUT
     * Endpoint: /api/categories/{id}
     * 
     * @param id the unique identifier of the category to update (path variable)
     * @param requestDTO the updated category data containing name and description (validated)
     * @return ResponseEntity with updated category data and HTTP 200 (OK) status
     * @throws ResourceNotFoundException if category not found (returns HTTP 404)
     * @throws DuplicateResourceException if new name already exists (returns HTTP 409)
     * @throws MethodArgumentNotValidException if validation fails (returns HTTP 400)
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update category", description = "Update an existing expense category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Category name already exists")
    })
    public ResponseEntity<ExpenseCategoryResponseDTO> updateCategory(
            @Parameter(description = "ID of the category to update") @PathVariable Long id,
            @Valid @RequestBody ExpenseCategoryRequestDTO requestDTO) {
        // Update category through service layer
        ExpenseCategoryResponseDTO response = categoryService.updateCategory(id, requestDTO);
        // Return 200 OK with updated category data
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes an expense category from the system.
     * 
     * HTTP Method: DELETE
     * Endpoint: /api/categories/{id}
     * 
     * @param id the unique identifier of the category to delete (path variable)
     * @return ResponseEntity with no content and HTTP 204 (NO_CONTENT) status
     * @throws ResourceNotFoundException if category not found (returns HTTP 404)
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category", description = "Delete an expense category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID of the category to delete") @PathVariable Long id) {
        // Delete category through service layer
        categoryService.deleteCategory(id);
        // Return 204 No Content (successful deletion)
        return ResponseEntity.noContent().build();
    }
}
