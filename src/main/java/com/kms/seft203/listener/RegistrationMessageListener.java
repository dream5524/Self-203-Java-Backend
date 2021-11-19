package com.kms.seft203.listener;

import com.kms.seft203.dto.EmailActivationDto;
import com.kms.seft203.service.EmailService;
import com.kms.seft203.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistrationMessageListener {
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(RegistrationMessageListener.class);
    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveRegistrationMessageFromQueue(EmailActivationDto emailActivationDto){
        emailService.sendEmailToVerify(emailActivationDto.getEmail(), emailActivationDto.getActivationLink());
        logger.info("Message processed from queue...");
    }
}
