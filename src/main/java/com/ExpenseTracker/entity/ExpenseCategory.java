package com.ExpenseTracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "expense_categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Expense> expenses = new HashSet<>();
}
