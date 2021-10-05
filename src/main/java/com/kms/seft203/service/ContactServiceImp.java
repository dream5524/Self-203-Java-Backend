package com.kms.seft203.service;

import com.kms.seft203.dto.ContactRequestDto;
import com.kms.seft203.dto.ContactResponseDto;
import com.kms.seft203.entity.Contact;
import com.kms.seft203.entity.User;
import com.kms.seft203.exception.EmailNotFoundException;
import com.kms.seft203.repository.ContactRepository;
import com.kms.seft203.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/*
 * ContactServiceImp class is defined for mapping, creating new contact, and handing methods
 * */

@Service
public class ContactServiceImp implements ContactService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ModelMapper modelMapper;

    /*
     * addContact() is defined for creating new contact
     * Input: Email and check if email does not exist, throw new EmailNotFoundException.
     * Output: Otherwise, create new contact and save this contact.
     * */
    @Override
    public List<ContactResponseDto> getAllContact() {
        List<Contact> listContacts = contactRepository.findAll();
        List<ContactResponseDto> listContactResponseDto = new ArrayList<>();

        for (Contact contact : listContacts) {
            //convert entity to dto
            ContactResponseDto contactResponse = modelMapper.map(contact, ContactResponseDto.class);
            listContactResponseDto.add(contactResponse);
        }
        return listContactResponseDto;
    }

    @Override
    public ContactRequestDto addContact(ContactRequestDto newContact) throws EmailNotFoundException {
        User user = findByEmail(newContact);
        if (user == null) {
            throw new EmailNotFoundException("Email " + newContact.getEmail() + " does not exist");
        }
        //Convert DTO to entity
        Contact contact = modelMapper.map(newContact, Contact.class);
        contact.setDateCreated(LocalDateTime.now());
        contact.setUser(user);

        Contact createContact = contactRepository.save(contact);
        //convert entity to DTO
        return modelMapper.map(createContact, ContactRequestDto.class);
    }

    //This method is defined for finding user by email and returning user.
    public User findByEmail(ContactRequestDto contactRequestDTO) {
        Optional<User> userOptional = userRepository.findByEmail(contactRequestDTO.getEmail());
        if (userOptional.isEmpty()) {
            return null;
        }
        return userOptional.get();
    }
}
