package com.ExpenseTracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple health check REST controller.
 * Provides a basic endpoint to verify that the Spring Boot application is running.
 * Useful for monitoring tools, load balancers, and deployment verification.
 * 
 * Base URL: /api
 * 
 * This is a minimal controller without complex business logic or dependencies.
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    /**
     * Health check endpoint.
     * Returns a simple confirmation message indicating the application is running.
     * 
     * HTTP Method: GET
     * Endpoint: /api/health
     * 
     * Use cases:
     * - Container orchestration health probes
     * - Load balancer health checks
     * - Monitoring and alerting systems
     * - Quick deployment verification
     * 
     * @return String message confirming application status
     */
    @GetMapping("/health")
    public String health() {
        // Return simple status message
        return "Application is running!";
    }
}
