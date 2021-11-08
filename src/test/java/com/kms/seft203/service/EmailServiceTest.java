package com.kms.seft203.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@SpringBootTest
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @MockBean
    private JavaMailSender javaMailSender;

    @Test
    void testEmailService_whenSuccess() {
        String email = "nvdloc@apcs.vn";
        String subject = "Activate account";
        String text = "Pls click the link below to activate your account:\n";
        String activationLink = "localhost:8000/verify?code=1234";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text + activationLink);
        Mockito.doNothing().when(javaMailSender).send(message);

        emailService.sendEmailToVerify(email, activationLink);

        Mockito.verify(javaMailSender, Mockito.times(1)).send(message);
    }
}
