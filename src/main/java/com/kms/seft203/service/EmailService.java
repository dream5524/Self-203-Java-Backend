package com.kms.seft203.service;

public interface EmailService {
    void sendEmailToVerify(String receiver, String subject, String text);
}
