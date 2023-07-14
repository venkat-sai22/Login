package com.example.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.login.entity.*;
import com.example.login.repository.LoginRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private LoginRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if initial user already exists
        if (!userRepository.existsByUsername("admin")) {
            // Create initial user
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("password"));

            // Save the user to the repository
            userRepository.save(user);
        }
    }
}

