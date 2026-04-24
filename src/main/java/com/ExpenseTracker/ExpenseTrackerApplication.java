package com.ExpenseTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class ExpenseTrackerApplication {

	public static void main(String[] args) {
		// Load .env file
		Dotenv dotenv = Dotenv.configure()
				.directory("./")
				.ignoreIfMissing()
				.load();
		
		// Set environment variables
		dotenv.entries().forEach(entry -> 
			System.setProperty(entry.getKey(), entry.getValue())
		);
		
		SpringApplication.run(ExpenseTrackerApplication.class, args);
	}

}
