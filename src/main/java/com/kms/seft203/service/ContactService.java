package com.kms.seft203.service;

import com.kms.seft203.dto.ContactRequestDto;
import com.kms.seft203.dto.ContactResponseDto;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.exception.EmailNotFoundException;
import java.util.List;
import java.util.Map;

public interface ContactService {
    ContactRequestDto addContact(ContactRequestDto contactRequestDTO) throws EmailNotFoundException;

    ContactResponseDto updateByEmail(ContactRequestDto contactRequestDto) throws ContactNotFoundException;

    List<ContactResponseDto> getAllByFilter(Integer id, String fullName, String title, Integer page, Integer size);

    Map<String, Object> countByTitle();
}
