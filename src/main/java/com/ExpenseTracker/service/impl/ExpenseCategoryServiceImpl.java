package com.ExpenseTracker.service.impl;

import com.ExpenseTracker.dto.ExpenseCategoryRequestDTO;
import com.ExpenseTracker.dto.ExpenseCategoryResponseDTO;
import com.ExpenseTracker.entity.ExpenseCategory;
import com.ExpenseTracker.exception.DuplicateResourceException;
import com.ExpenseTracker.exception.ResourceNotFoundException;
import com.ExpenseTracker.repository.ExpenseCategoryRepository;
import com.ExpenseTracker.service.ExpenseCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the ExpenseCategoryService interface.
 * Handles all business logic related to expense category management including CRUD operations.
 * Ensures category names are unique across the system.
 * Uses Spring Data JPA for database operations and implements transaction management.
 * 
 * @see ExpenseCategoryService
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ExpenseCategoryServiceImpl implements ExpenseCategoryService {

    private final ExpenseCategoryRepository categoryRepository;

    /**
     * Creates a new expense category.
     * Validates category name uniqueness before persisting to the database.
     * 
     * @param requestDTO the category creation data containing name and description
     * @return the created expense category response with generated ID
     * @throws DuplicateResourceException if a category with the given name already exists
     */
    @Override
    public ExpenseCategoryResponseDTO createCategory(ExpenseCategoryRequestDTO requestDTO) {
        log.info("Creating expense category: {}", requestDTO.getName());

        // Validate name uniqueness - ensure no duplicate category names in the system
        if (categoryRepository.existsByName(requestDTO.getName())) {
            throw new DuplicateResourceException("ExpenseCategory", "name", requestDTO.getName());
        }

        // Convert DTO to entity and persist to database
        ExpenseCategory category = mapToEntity(requestDTO);
        ExpenseCategory savedCategory = categoryRepository.save(category);

        log.info("Successfully created category with ID: {}", savedCategory.getId());
        return mapToResponseDTO(savedCategory);
    }

    /**
     * Retrieves all expense categories from the database.
     * Uses read-only transaction for optimized performance.
     * 
     * @return a list of all expense categories as response DTOs, empty list if no categories exist
     */
    @Override
    @Transactional(readOnly = true)
    public List<ExpenseCategoryResponseDTO> getAllCategories() {
        log.info("Fetching all expense categories");
        
        // Fetch all categories and transform to DTOs using Java streams
        return categoryRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific expense category by its unique identifier.
     * Uses read-only transaction for optimized performance.
     * 
     * @param id the unique identifier of the category to retrieve
     * @return the expense category response DTO
     * @throws ResourceNotFoundException if no category exists with the given ID
     */
    @Override
    @Transactional(readOnly = true)
    public ExpenseCategoryResponseDTO getCategoryById(Long id) {
        log.info("Fetching category with ID: {}", id);
        
        // Find category by ID or throw exception if not found
        ExpenseCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExpenseCategory", "id", id));
        return mapToResponseDTO(category);
    }

    /**
     * Updates an existing expense category.
     * Validates that the new name (if changed) is not already in use by another category.
     * 
     * @param id the unique identifier of the category to update
     * @param requestDTO the updated category data containing name and description
     * @return the updated expense category response DTO
     * @throws ResourceNotFoundException if no category exists with the given ID
     * @throws DuplicateResourceException if the new name already exists for another category
     */
    @Override
    public ExpenseCategoryResponseDTO updateCategory(Long id, ExpenseCategoryRequestDTO requestDTO) {
        log.info("Updating category with ID: {}", id);

        // Verify category exists before attempting update
        ExpenseCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExpenseCategory", "id", id));

        // Validate name uniqueness if name is being changed
        // Only check if the new name is different from current name
        if (!category.getName().equals(requestDTO.getName()) && 
            categoryRepository.existsByName(requestDTO.getName())) {
            throw new DuplicateResourceException("ExpenseCategory", "name", requestDTO.getName());
        }

        // Update category fields with new values
        category.setName(requestDTO.getName());
        category.setDescription(requestDTO.getDescription());

        // Persist changes to database
        ExpenseCategory updatedCategory = categoryRepository.save(category);
        log.info("Successfully updated category with ID: {}", id);
        return mapToResponseDTO(updatedCategory);
    }

    /**
     * Deletes an expense category from the system.
     * Verifies category existence before attempting deletion.
     * 
     * @param id the unique identifier of the category to delete
     * @throws ResourceNotFoundException if no category exists with the given ID
     */
    @Override
    public void deleteCategory(Long id) {
        log.info("Deleting category with ID: {}", id);

        // Verify category exists before deletion
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("ExpenseCategory", "id", id);
        }

        // Remove category from database
        categoryRepository.deleteById(id);
        log.info("Successfully deleted category with ID: {}", id);
    }

    /**
     * Maps an ExpenseCategoryRequestDTO to an ExpenseCategory entity.
     * Helper method for converting request data transfer objects to domain entities.
     * 
     * @param dto the expense category request DTO containing category data
     * @return a new ExpenseCategory entity populated with data from the DTO
     */
    private ExpenseCategory mapToEntity(ExpenseCategoryRequestDTO dto) {
        ExpenseCategory category = new ExpenseCategory();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }

    /**
     * Maps an ExpenseCategory entity to an ExpenseCategoryResponseDTO.
     * Helper method for converting domain entities to response data transfer objects.
     * 
     * @param category the ExpenseCategory entity to convert
     * @return an ExpenseCategoryResponseDTO containing category information suitable for API responses
     */
    private ExpenseCategoryResponseDTO mapToResponseDTO(ExpenseCategory category) {
        return new ExpenseCategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}
