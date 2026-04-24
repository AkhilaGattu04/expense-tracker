package com.ExpenseTracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a User Profile.
 * Mapped to 'user_profiles' table.
 */
@Entity
@Table(name = "user_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String phone;

    @Column(length = 255)
    private String address;

    @Column(length = 20)
    private String currency;

    // One-to-One relationship with User
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
