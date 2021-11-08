package com.kms.seft203.service;

public interface EmailService {
    void sendEmailToVerify(String receiverEmail, String activationLink);
}
