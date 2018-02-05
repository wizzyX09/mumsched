package edu.mum.mumsched.service;

public interface IEmailService {
    public void sendSimpleMessage(String to, String subject, String text);
    public void sendGeneratedAccountMail(String email, String password);
}
