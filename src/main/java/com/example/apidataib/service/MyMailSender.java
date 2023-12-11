package com.example.apidataib.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MyMailSender {
    @Autowired
    private JavaMailSender javamailSender;
    @Value("${spring.mail.username}")
    private String username;
    private final String emailTo = "mastergtavrp@yandex.ru";

    public void send(String subject, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        javamailSender.send(mailMessage);
    }

}

// Временная почта
// xabef45861@gyxmz.com