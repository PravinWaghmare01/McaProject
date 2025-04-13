package com.example.organdonation.controller;

import com.example.organdonation.model.User;
import com.example.organdonation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    
    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        User createdUser  = userService.registerUser (user);
        return ResponseEntity.status(201).body(createdUser );
    }

    
    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody User user) {
        User existingUser  = userService.findByUsername(user.getUsername());
        if (existingUser  != null && new BCryptPasswordEncoder().matches(user.getPassword(), existingUser .getPassword())) {
            return ResponseEntity.ok("Sign in successful");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}