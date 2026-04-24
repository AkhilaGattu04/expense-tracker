package com.ExpenseTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for ExpenseCategory
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseCategoryResponseDTO {

    private Long id;
    private String name;
    private String description;
}
