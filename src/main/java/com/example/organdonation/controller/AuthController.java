package com.example.organdonation.controller;

import com.example.organdonation.model.PasswordResetToken;
import com.example.organdonation.model.User;
import com.example.organdonation.service.EmailService;
import com.example.organdonation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService; // Inject the EmailService

    // Endpoint to request a password reset
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User  not found");
        }
        String token = userService.createPasswordResetTokenForUser (user);
        
        // Send the password reset email
        emailService.sendPasswordResetEmail(user.getEmail(), token);
        
        return ResponseEntity.ok("Password reset link sent to your email");
    }

    // Endpoint to reset the password
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        PasswordResetToken passwordResetToken = userService.validatePasswordResetToken(token);
        
        if (passwordResetToken == null) {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }

        User user = passwordResetToken.getUser ();
        user.setPassword(newPassword); // Hash the password before saving
        userService.registerUser (user); // Save the updated user
     
        return ResponseEntity.ok("Password has been reset successfully");
    }
 
}