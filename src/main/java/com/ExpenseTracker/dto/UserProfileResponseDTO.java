package com.ExpenseTracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for UserProfile
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponseDTO {

    private Long id;
    private String phone;
    private String address;
    private String currency;
    private Long userId;
}
