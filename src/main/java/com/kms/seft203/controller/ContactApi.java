package com.kms.seft203.controller;

import com.kms.seft203.dto.ContactRequestDTO;
import com.kms.seft203.exception.EmailNotFoundException;
import com.kms.seft203.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
public class ContactApi {
    private static final Logger logger = LoggerFactory.getLogger(ContactApi.class);
    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactRequestDTO> addContact(@RequestBody ContactRequestDTO contactDtoReq) throws EmailNotFoundException {
        logger.info("create contact method started {}", contactDtoReq);
        ContactRequestDTO contactDTOResponse = contactService.addContact(contactDtoReq);
        return new ResponseEntity<>(contactDTOResponse, HttpStatus.CREATED);
    }
}
