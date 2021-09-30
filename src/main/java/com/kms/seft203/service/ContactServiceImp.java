package com.kms.seft203.service;

import com.kms.seft203.dto.ContactRequestDTO;
import com.kms.seft203.entity.Contact;
import com.kms.seft203.repository.ContactRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ContactServiceImp implements ContactService {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ContactRequestDTO addContact(ContactRequestDTO newContact) {
        //Convert DTO to entity
        Contact contact = modelMapper.map(newContact, Contact.class);
        contact.setDateCreated(LocalDateTime.now());
        contact.setUser(newContact.getUser());
        Contact createContact = contactRepository.save(contact);
        //convert entity to DTO
        return modelMapper.map(createContact, ContactRequestDTO.class);

    }
}
