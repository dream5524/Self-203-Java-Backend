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
    public void sendEmailToVerify(String receiverEmail, String subject, String text) {
        log.info("sending activation email to {}, subject: '{}', message: '{}'", receiverEmail, subject, text);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiverEmail);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
