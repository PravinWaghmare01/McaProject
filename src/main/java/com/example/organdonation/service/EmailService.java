package com.example.organdonation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String token) {
        String subject = "Password Reset Request";
        String resetLink = "http://localhost:8080/api/auth/reset-password?token=" + token; // Adjust the URL as needed
        String message = "To reset your password, click the link below:\n" + resetLink;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        email.setFrom("waghmarepravin7030@gmail.com"); // Set the sender's email

        mailSender.send(email);
    }
}