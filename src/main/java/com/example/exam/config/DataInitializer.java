package com.example.exam.config;

import com.example.exam.model.User;
import com.example.exam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Seeds a default admin account on application startup if one doesn't already
 * exist.
 * Default credentials:
 * Username: admin@exam.com
 * Password: admin123
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Only create the admin if no admin account exists yet
        if (userRepository.findByRole("ROLE_ADMIN").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin@exam.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ROLE_ADMIN");
            admin.setFullName("Administrator");
            userRepository.save(admin);

            System.out.println("=============================================");
            System.out.println("  DEFAULT ADMIN ACCOUNT CREATED");
            System.out.println("  Username: admin@exam.com");
            System.out.println("  Password: admin123");
            System.out.println("  ** Please change the password after login **");
            System.out.println("=============================================");
        } else {
            System.out.println("Admin account already exists. Skipping seed.");
        }
    }
}
