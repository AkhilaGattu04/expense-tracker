package com.ExpenseTracker.service.impl;

import com.ExpenseTracker.dto.ExpenseCategoryResponseDTO;
import com.ExpenseTracker.dto.ExpenseRequestDTO;
import com.ExpenseTracker.dto.ExpenseResponseDTO;
import com.ExpenseTracker.entity.Expense;
import com.ExpenseTracker.entity.ExpenseCategory;
import com.ExpenseTracker.entity.User;
import com.ExpenseTracker.exception.ResourceNotFoundException;
import com.ExpenseTracker.repository.ExpenseCategoryRepository;
import com.ExpenseTracker.repository.ExpenseRepository;
import com.ExpenseTracker.repository.UserRepository;
import com.ExpenseTracker.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final ExpenseCategoryRepository categoryRepository;

    @Override
    public ExpenseResponseDTO createExpense(ExpenseRequestDTO requestDTO) {
        log.info("Creating expense: {}", requestDTO.getTitle());

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", requestDTO.getUserId()));

        Set<ExpenseCategory> categories = resolveCategories(requestDTO.getCategoryIds());

        Expense expense = mapToEntity(requestDTO, user, categories);
        Expense savedExpense = expenseRepository.save(expense);

        log.info("Successfully created expense with ID: {}", savedExpense.getId());
        return mapToResponseDTO(savedExpense);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponseDTO> getAllExpenses() {
        log.info("Fetching all expenses");
        return expenseRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ExpenseResponseDTO getExpenseById(Long id) {
        log.info("Fetching expense with ID: {}", id);
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", id));
        return mapToResponseDTO(expense);
    }

    @Override
    public ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO requestDTO) {
        log.info("Updating expense with ID: {}", id);

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", id));

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", requestDTO.getUserId()));

        Set<ExpenseCategory> categories = resolveCategories(requestDTO.getCategoryIds());

        expense.setTitle(requestDTO.getTitle());
        expense.setAmount(requestDTO.getAmount());
        expense.setExpenseDate(requestDTO.getExpenseDate());
        expense.setNotes(requestDTO.getNotes());
        expense.setUser(user);
        expense.setCategories(categories);

        Expense updatedExpense = expenseRepository.save(expense);
        log.info("Successfully updated expense with ID: {}", id);
        return mapToResponseDTO(updatedExpense);
    }

    @Override
    public void deleteExpense(Long id) {
        log.info("Deleting expense with ID: {}", id);
        if (!expenseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Expense", "id", id);
        }
        expenseRepository.deleteById(id);
        log.info("Successfully deleted expense with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponseDTO> getExpensesByUserId(Long userId) {
        log.info("Fetching expenses for user ID: {}", userId);
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
        return expenseRepository.findByUserId(userId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponseDTO> getExpensesByCategoryId(Long categoryId) {
        log.info("Fetching expenses for category ID: {}", categoryId);
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("ExpenseCategory", "id", categoryId);
        }
        return expenseRepository.findByCategoriesId(categoryId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private Set<ExpenseCategory> resolveCategories(Set<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            throw new ResourceNotFoundException("At least one category is required");
        }
        Set<ExpenseCategory> categories = new HashSet<>(categoryRepository.findAllById(categoryIds));
        if (categories.size() != categoryIds.size()) {
            throw new ResourceNotFoundException("One or more categories not found");
        }
        return categories;
    }

    private Expense mapToEntity(ExpenseRequestDTO dto, User user, Set<ExpenseCategory> categories) {
        Expense expense = new Expense();
        expense.setTitle(dto.getTitle());
        expense.setAmount(dto.getAmount());
        expense.setExpenseDate(dto.getExpenseDate());
        expense.setNotes(dto.getNotes());
        expense.setUser(user);
        expense.setCategories(categories);
        return expense;
    }

    private ExpenseResponseDTO mapToResponseDTO(Expense expense) {
        Set<ExpenseCategoryResponseDTO> categoryDTOs = expense.getCategories().stream()
                .map(c -> new ExpenseCategoryResponseDTO(c.getId(), c.getName(), c.getDescription()))
                .collect(Collectors.toSet());

        return new ExpenseResponseDTO(
                expense.getId(),
                expense.getTitle(),
                expense.getAmount(),
                expense.getExpenseDate(),
                expense.getNotes(),
                expense.getCreatedAt(),
                expense.getUser().getId(),
                expense.getUser().getName(),
                categoryDTOs
        );
    }
}
