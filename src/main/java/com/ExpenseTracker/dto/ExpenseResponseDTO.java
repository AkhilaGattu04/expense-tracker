package com.ExpenseTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponseDTO {

    private Long id;
    private String title;
    private BigDecimal amount;
    private LocalDate expenseDate;
    private String notes;
    private LocalDateTime createdAt;
    private Long userId;
    private String userName;
    private Set<ExpenseCategoryResponseDTO> categories;
}
