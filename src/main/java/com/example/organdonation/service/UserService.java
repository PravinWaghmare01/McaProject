package com.example.organdonation.service;

import com.example.organdonation.model.PasswordResetToken;
import com.example.organdonation.model.User;
import com.example.organdonation.repository.PasswordResetTokenRepository;
import com.example.organdonation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    public User registerUser (User user) {
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String createPasswordResetTokenForUser (User user) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser (user);
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(1)); // Token valid for 1 hour
        passwordResetTokenRepository.save(passwordResetToken);
        return token;
    }

    public PasswordResetToken validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null || passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return null; // Token is invalid or expired
        }
        return passwordResetToken;
    }
}