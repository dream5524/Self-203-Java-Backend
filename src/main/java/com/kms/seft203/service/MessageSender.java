package com.kms.seft203.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {
    public void sendMessageToQueue(RabbitTemplate rabbitTemplate, String exchange, String routingKey, Object object){
        rabbitTemplate.convertAndSend(exchange, routingKey, object);
    }
}
