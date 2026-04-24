package com.ExpenseTracker.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequestDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 150, message = "Title must not exceed 150 characters")
    private String title;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Amount must have at most 10 integer digits and 2 decimal places")
    private BigDecimal amount;

    @NotNull(message = "Expense date is required")
    private LocalDate expenseDate;

    @Size(max = 255, message = "Notes must not exceed 255 characters")
    private String notes;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "At least one category ID is required")
    @Size(min = 1, message = "At least one category ID is required")
    private Set<Long> categoryIds;
}
