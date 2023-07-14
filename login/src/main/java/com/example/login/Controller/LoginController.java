package com.example.login.Controller;



import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.login.config.JwtTokenUtil;
import com.example.login.entity.JwtResponse;
import com.example.login.entity.LoginRequest;
import com.example.login.entity.SignupRequest;
import com.example.login.entity.User;
import com.example.login.repository.LoginRepository;
import com.example.login.service.UserService;

//UserController.java

@RestController
@RequestMapping("/api/users")
public class LoginController {

	@Autowired
	private PasswordEncoder passwordEncoder;
 @Autowired
 private LoginRepository userRepository;
@Autowired
private UserService userService;
 @Autowired
 private AuthenticationManager authenticationManager;

 @Autowired
 private JwtTokenUtil jwtTokenUtil;

 @PostMapping("/signup")
 public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest) {
     // Validate user input
     if (userRepository.existsByUsername(signupRequest.getUsername())) {
         return ResponseEntity.badRequest().body("Username is already taken");
     }

     // Create and save new user
     User user = new User(signupRequest.getUsername(), encodePassword(signupRequest.getPassword()));
     userRepository.save(user);

     return ResponseEntity.ok("User registered successfully");
 }

 @PostMapping("/login")
 public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
     // Authenticate user
	 authenticationManager.authenticate(
	         new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

     // Generate JWT token
     UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
     String token = jwtTokenUtil.generateToken(userDetails);

     return ResponseEntity.ok(new JwtResponse(token));
 }

 @GetMapping("/hello")
 public ResponseEntity<?> hello() {
     return ResponseEntity.ok("Hello from GreenStitch");
 }

 private String encodePassword(String password) {
     // Implement password hashing logic using a secure hashing algorithm (e.g., bcrypt)
     return passwordEncoder.encode(password);
 }
}
