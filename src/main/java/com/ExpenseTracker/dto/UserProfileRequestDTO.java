package com.ExpenseTracker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for UserProfile creation and updates
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequestDTO {

    @Size(max = 20, message = "Phone must not exceed 20 characters")
    private String phone;

    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    @Size(max = 20, message = "Currency must not exceed 20 characters")
    private String currency;

    @NotNull(message = "User ID is required")
    private Long userId;
}
