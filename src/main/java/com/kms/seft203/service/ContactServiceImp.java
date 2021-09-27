package com.kms.seft203.service;

import com.kms.seft203.dto.ContactRequestDTO;
import com.kms.seft203.entity.Contact;
import com.kms.seft203.repository.ContactRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ContactServiceImp implements ContactService {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Contact> getAllContact() {
        List<Contact> listContacts = contactRepository.findAll();
        return listContacts;
    }
    @Override
    public ContactRequestDTO addContact(ContactRequestDTO newContact) {
        //Convert DTO to entity
        Contact contact = modelMapper.map(newContact, Contact.class);
        Contact createContact = contactRepository.save(contact);
        //convert entity to DTO
        ContactRequestDTO contactRequestDTO = modelMapper.map(createContact, ContactRequestDTO.class);
        return contactRequestDTO;
    }
}
