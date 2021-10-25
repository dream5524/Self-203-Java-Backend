package com.kms.seft203.controller;

import com.kms.seft203.dto.ContactRequestDto;
import com.kms.seft203.dto.ContactResponseDto;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.exception.EmailNotFoundException;
import com.kms.seft203.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactApi {
    private static final Logger logger = LoggerFactory.getLogger(ContactApi.class);
    @Autowired
    private ContactService contactService;

    @GetMapping
    public ResponseEntity<List<ContactResponseDto>> getAllContact(){
        logger.info("Get all contact method started...");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contactService.getAllContact());
    }

    @PostMapping
    public ResponseEntity<ContactRequestDto> addContact(@Valid @RequestBody ContactRequestDto contactDtoReq) throws EmailNotFoundException {
        logger.info("Creating contact method has begun... {}", contactDtoReq);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contactService.addContact(contactDtoReq));
    }
    @PutMapping
    public ResponseEntity<String> updateByEmail(@Valid @RequestBody ContactRequestDto contactRequestDto) throws ContactNotFoundException {
        logger.info("Updating contact method has begun... {}",contactRequestDto);
        contactService.updateByEmail(contactRequestDto);
        return ResponseEntity.ok("The information were successful updated !");
    }

}
