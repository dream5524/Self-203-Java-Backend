package com.kms.seft203.service;

import com.kms.seft203.dto.ContactRequestDTO;
import com.kms.seft203.exception.EmailNotFoundException;
import java.util.List;

public interface ContactService {
    ContactRequestDTO addContact(ContactRequestDTO contactRequestDTO) throws EmailNotFoundException;
    List<ContactRequestDTO> getAllContact();
}
