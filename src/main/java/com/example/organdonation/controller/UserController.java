package com.example.organdonation.controller;

import com.example.organdonation.model.User;
import com.example.organdonation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        User createdUser   = userService.registerUser (user);
        return ResponseEntity.status(201).body(createdUser );
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody User user) {
        User existingUser   = userService.findByUsername(user.getUsername());
        if (existingUser  != null && user.getPassword().equals(existingUser .getPassword())) {
            return ResponseEntity.ok("Sign in successful");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        User user = userService.findByEmail(email); // Change to email
        if (user != null) {
            // Here you would send an email with a reset link
            // For example: emailService.sendPasswordResetEmail(user);
            return ResponseEntity.ok("Password reset link sent to your email.");
        } else {
            return ResponseEntity.status(404).body("User  not found.");
        }
    }
}