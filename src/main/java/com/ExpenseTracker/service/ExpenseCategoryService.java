package com.ExpenseTracker.service;

import com.ExpenseTracker.dto.ExpenseCategoryRequestDTO;
import com.ExpenseTracker.dto.ExpenseCategoryResponseDTO;

import java.util.List;

/**
 * Service interface for ExpenseCategory operations.
 * Provides business logic for managing expense categories used to classify expenses.
 * Categories help organize and group expenses by type (e.g., Food, Transport, Entertainment).
 */
public interface ExpenseCategoryService {

    /**
     * Creates a new expense category.
     * Validates that the category name is unique before creation.
     * 
     * @param requestDTO the category creation data containing name and description
     * @return the created expense category response with generated ID
     * @throws DuplicateResourceException if a category with the given name already exists
     */
    ExpenseCategoryResponseDTO createCategory(ExpenseCategoryRequestDTO requestDTO);

    /**
     * Retrieves all expense categories from the system.
     * 
     * @return a list of all expense categories as response DTOs, empty list if no categories exist
     */
    List<ExpenseCategoryResponseDTO> getAllCategories();

    /**
     * Retrieves a specific expense category by its unique identifier.
     * 
     * @param id the unique identifier of the category to retrieve
     * @return the expense category response DTO
     * @throws ResourceNotFoundException if no category exists with the given ID
     */
    ExpenseCategoryResponseDTO getCategoryById(Long id);

    /**
     * Updates an existing expense category.
     * Validates that the new name (if changed) is unique before updating.
     * 
     * @param id the unique identifier of the category to update
     * @param requestDTO the updated category data containing name and description
     * @return the updated expense category response DTO
     * @throws ResourceNotFoundException if no category exists with the given ID
     * @throws DuplicateResourceException if the new name already exists for another category
     */
    ExpenseCategoryResponseDTO updateCategory(Long id, ExpenseCategoryRequestDTO requestDTO);

    /**
     * Deletes an expense category from the system.
     * 
     * @param id the unique identifier of the category to delete
     * @throws ResourceNotFoundException if no category exists with the given ID
     */
    void deleteCategory(Long id);
}
