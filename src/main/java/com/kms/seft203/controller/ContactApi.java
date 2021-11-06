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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactApi {
    private static final Logger logger = LoggerFactory.getLogger(ContactApi.class);
    @Autowired
    private ContactService contactService;

    @GetMapping
    public ResponseEntity<List<ContactResponseDto>> getAllByFilter(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        logger.info("Getting all contact method has begun...");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contactService.getAllByFilter(id, fullName, title, page, size));
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
        logger.info("Updating contact method has begun... {}", contactRequestDto);
        contactService.updateByEmail(contactRequestDto);
        return ResponseEntity.ok("The information were successful updated !");
    }
}
