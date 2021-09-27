package com.kms.seft203.service;

import com.kms.seft203.dto.ContactRequestDTO;
import com.kms.seft203.entity.Contact;
import lombok.Setter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
public interface ContactService {
    List<Contact> getAllContact();
    ContactRequestDTO addContact(ContactRequestDTO contactRequestDTO);

}
