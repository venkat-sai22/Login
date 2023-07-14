package com.example.login.service;
//UserService.java

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.login.entity.User;
import com.example.login.repository.LoginRepository;

@Service
public class UserService implements UserDetailsService {

 @Autowired
 private LoginRepository userRepository;

 @Autowired
 private PasswordEncoder passwordEncoder;

 @Override
 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     User user = userRepository.findByUsername(username);
     if (user == null) {
         throw new UsernameNotFoundException("User not found");
     }
     return new org.springframework.security.core.userdetails.User(
             user.getUsername(), user.getPassword(), Collections.emptyList());
 }

 public User signUp(String username, String password) {
     // Validate user input
     if (userRepository.existsByUsername(username)) {
         throw new IllegalArgumentException("Username is already taken");
     }

     // Create and save new user
     User user = new User(username, encodePassword(password));
     return userRepository.save(user);
 }

 private String encodePassword(String password) {
     // Implement password hashing logic using a secure hashing algorithm (e.g., bcrypt)
     return passwordEncoder.encode(password);
 }
}
