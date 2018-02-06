package edu.mum.mumsched.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class IEmailServiceImpl implements IEmailService {

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendGeneratedAccountMail(String email, String password) {
        sendSimpleMessage(
                email,
                "You have a new account",
                "Welcome to Mumsched! " +
                        "You can login to your account at http://localhost:8080/.\n" +
                        "Use your email to login: " + email + "\n" +
                        "We provide a temporary password: " + password);
    }
}