package com.ExpenseTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response DTO for User
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
