package com.kms.seft203.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service @Slf4j
public class EmailServiceImp implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmailToVerify(String email, String activationLink) {
        String subject = "Activate account";
        String text = "Pls click the link below to activate your account:\n";
        log.info("Sending activation email to {}, subject: '{}', message: '{}'", email, subject, activationLink);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text + activationLink);

        javaMailSender.send(message);
    }
}
