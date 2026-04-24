package com.ExpenseTracker.controller;

import com.ExpenseTracker.dto.ExpenseRequestDTO;
import com.ExpenseTracker.dto.ExpenseResponseDTO;
import com.ExpenseTracker.service.ExpenseService;
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

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@Tag(name = "Expense Management", description = "APIs for managing expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    @Operation(summary = "Create expense", description = "Create a new expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Expense created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "User or Category not found")
    })
    public ResponseEntity<ExpenseResponseDTO> createExpense(
            @Valid @RequestBody ExpenseRequestDTO requestDTO) {
        ExpenseResponseDTO response = expenseService.createExpense(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all expenses", description = "Retrieve all expenses")
    @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully")
    public ResponseEntity<List<ExpenseResponseDTO>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get expense by ID", description = "Retrieve a specific expense by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense found"),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    public ResponseEntity<ExpenseResponseDTO> getExpenseById(
            @Parameter(description = "ID of the expense to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update expense", description = "Update an existing expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense updated successfully"),
            @ApiResponse(responseCode = "404", description = "Expense not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ExpenseResponseDTO> updateExpense(
            @Parameter(description = "ID of the expense to update") @PathVariable Long id,
            @Valid @RequestBody ExpenseRequestDTO requestDTO) {
        return ResponseEntity.ok(expenseService.updateExpense(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete expense", description = "Delete an expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Expense deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    public ResponseEntity<Void> deleteExpense(
            @Parameter(description = "ID of the expense to delete") @PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get expenses by user", description = "Retrieve all expenses for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<ExpenseResponseDTO>> getExpensesByUserId(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        return ResponseEntity.ok(expenseService.getExpensesByUserId(userId));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get expenses by category", description = "Retrieve all expenses in a specific category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<List<ExpenseResponseDTO>> getExpensesByCategoryId(
            @Parameter(description = "Category ID") @PathVariable Long categoryId) {
        return ResponseEntity.ok(expenseService.getExpensesByCategoryId(categoryId));
    }
}
