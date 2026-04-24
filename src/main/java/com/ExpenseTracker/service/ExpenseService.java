package com.ExpenseTracker.service;

import com.ExpenseTracker.dto.ExpenseRequestDTO;
import com.ExpenseTracker.dto.ExpenseResponseDTO;

import java.util.List;

public interface ExpenseService {

    ExpenseResponseDTO createExpense(ExpenseRequestDTO requestDTO);

    List<ExpenseResponseDTO> getAllExpenses();

    ExpenseResponseDTO getExpenseById(Long id);

    ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO requestDTO);

    void deleteExpense(Long id);

    List<ExpenseResponseDTO> getExpensesByUserId(Long userId);

    List<ExpenseResponseDTO> getExpensesByCategoryId(Long categoryId);
}
