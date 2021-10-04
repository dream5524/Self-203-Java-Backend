package com.kms.seft203.service;

import com.kms.seft203.dto.ContactRequestDTO;
import com.kms.seft203.exception.EmailNotFoundException;

public interface ContactService {
    ContactRequestDTO addContact(ContactRequestDTO contactRequestDTO) throws EmailNotFoundException;
}
