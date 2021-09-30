package com.kms.seft203.service;

import com.kms.seft203.dto.ContactRequestDTO;
import com.kms.seft203.entity.Contact;
import com.kms.seft203.entity.User;
import com.kms.seft203.exception.EmailNotFoundException;
import com.kms.seft203.repository.ContactRepository;
import com.kms.seft203.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ContactServiceImp implements ContactService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ContactRequestDTO addContact(ContactRequestDTO newContact) throws EmailNotFoundException {
        User user = findUserByEmail(newContact).get();
        //Convert DTO to entity
        Contact contact = modelMapper.map(newContact, Contact.class);
        contact.setDateCreated(LocalDateTime.now());
        contact.setUser(user);

        Contact createContact = contactRepository.save(contact);
        //convert entity to DTO
        return modelMapper.map(createContact, ContactRequestDTO.class);
    }

    public Optional<User> findUserByEmail(ContactRequestDTO contactRequestDTO) throws EmailNotFoundException {
        Optional<User> userOptional = userRepository.findUserByEmail(contactRequestDTO.getEmail());
        if (userOptional.isEmpty()) {
            throw new EmailNotFoundException("Email" + contactRequestDTO.getEmail() + " does not exist");
        }
        return userOptional;
    }

}
