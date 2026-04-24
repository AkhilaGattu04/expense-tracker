package com.ExpenseTracker.repository;

import com.ExpenseTracker.entity.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for ExpenseCategory entity
 */
@Repository
public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {

    /**
     * Find category by name
     * @param name the category name
     * @return Optional containing category if found
     */
    Optional<ExpenseCategory> findByName(String name);

    /**
     * Check if category exists by name
     * @param name the category name
     * @return true if exists, false otherwise
     */
    boolean existsByName(String name);
}
