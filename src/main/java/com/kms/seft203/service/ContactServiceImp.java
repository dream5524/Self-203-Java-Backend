package com.kms.seft203.service;

import com.kms.seft203.dto.ContactRequestDto;
import com.kms.seft203.dto.ContactResponseDto;
import com.kms.seft203.entity.Contact;
import com.kms.seft203.entity.User;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.exception.EmailNotFoundException;
import com.kms.seft203.repository.ContactRepository;
import com.kms.seft203.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    public ContactResponseDto updateByEmail(ContactRequestDto contactRequestDto) throws ContactNotFoundException {
        Optional<Contact> contactOptional = contactRepository.findByEmail(contactRequestDto.getEmail());
        if (contactOptional.isEmpty()) {
            throw new ContactNotFoundException("Email " + contactRequestDto.getEmail() + " does not exist");
        }
        Contact contact = contactOptional.get();
        contact.setFirstName(contactRequestDto.getFirstName());
        contact.setLastName(contactRequestDto.getLastName());
        contact.setTitle(contactRequestDto.getTitle());
        contact.setProject(contactRequestDto.getProject());
        Contact saveContact = contactRepository.save(contact);
        return modelMapper.map(saveContact, ContactResponseDto.class);

    }

    @Override
    public List<ContactResponseDto> getAllByFilter(Integer id, String fullName, String title, Integer page, Integer size) {
        if (page == null && size == null) {
            page = 0;
            size = 10;
        }
        Pageable pageable = PageRequest.of(page, size);

        Page<Contact> contactFromDb = contactRepository.findAllByInputField(id, fullName, title, pageable);
        return contactFromDb.stream().map(contact -> modelMapper.map(contact, ContactResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ContactRequestDto addContact(ContactRequestDto newContact) throws EmailNotFoundException {
        User user = findByEmail(newContact.getEmail());
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

    /*This method is defined for finding user by email.
    If find a specific email, it'll return user. Otherwise, return null.
     */
    public User findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return null;
        }
        return userOptional.get();
    }
}
