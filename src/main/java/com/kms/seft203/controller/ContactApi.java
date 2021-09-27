package com.kms.seft203.controller;

import com.kms.seft203.entity.Contact;
import com.kms.seft203.dto.ContactRequestDTO;
import com.kms.seft203.service.ContactService;
import com.kms.seft203.service.ContactServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ContactApi {
    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImp.class);
    @Autowired
    private ContactService contactService;

    @GetMapping("/contacts")
    public List<Contact> getAllContact() {
        return contactService.getAllContact();
    }

    @PostMapping("contacts")
    public ResponseEntity<ContactRequestDTO> addContact(@RequestBody ContactRequestDTO contactDtoReq) {
        logger.debug("createOrUpdateEmployee method started {}", contactDtoReq);
        ContactRequestDTO contactDTOResponse = contactService.addContact(contactDtoReq);
        return new ResponseEntity<>(contactDTOResponse, HttpStatus.CREATED);
    }


}
