package com.kms.seft203.service;

import com.kms.seft203.dto.ContactRequestDto;
import com.kms.seft203.dto.ContactResponseDto;
import com.kms.seft203.entity.Contact;
import com.kms.seft203.entity.User;
import com.kms.seft203.exception.EmailNotFoundException;
import com.kms.seft203.repository.ContactRepository;
import com.kms.seft203.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
class ContactServiceTest {
    @Autowired
    private ContactService contactService;
    @MockBean
    private ContactRepository contactRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    void getAllContactServiceTest() {
        List<Contact> contactList = Stream.of(
                        new Contact("Nguyen Chi",
                                "Pheo",
                                new User(1, "pheonc@gmail.com", "1", "Nguyen Chi Pheo"),
                                "Tester",
                                "Implement API"))
                .collect(Collectors.toList());
        Mockito.when(contactRepository.findAll()).thenReturn(contactList);

        List<ContactResponseDto> contactResponseDtoList = Stream.of(
                        new ContactResponseDto("Nguyen Chi",
                                "Pheo",
                                new User(1, "pheonc@gmail.com", "1", "Nguyen Chi Pheo"),
                                "Tester",
                                "Implement API"))
                .collect(Collectors.toList());

        contactResponseDtoList = contactService.getAllContact();

        Assert.assertEquals(contactResponseDtoList.get(0).getFirstName(), contactList.get(0).getFirstName());
        Assert.assertEquals(contactResponseDtoList.get(0).getLastName(), contactList.get(0).getLastName());
        Assert.assertEquals(contactResponseDtoList.get(0).getTitle(), contactList.get(0).getTitle());
        Assert.assertEquals(contactResponseDtoList.get(0).getProject(), contactList.get(0).getProject());
        Assert.assertEquals(contactResponseDtoList.get(0).getUser(), contactList.get(0).getUser());
        Assert.assertEquals(contactResponseDtoList.stream().count(), 1);
    }

    @Test
    void createContactServiceTest() throws EmailNotFoundException {
        User user = new User(1, "huyenmo@gmail.com", "2", "Huyen Mo");
        Contact contact = new Contact("Huyen", "Mo", user, "demo", "demo");
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(java.util.Optional.of(user));

        Mockito.when(contactRepository.save(Mockito.any(Contact.class))).thenReturn(contact);
        ContactRequestDto contactRequestDto = new ContactRequestDto("huyenmo@gmail.com", "Huyen", "Mo", "demo", "demo");
        ContactRequestDto contactSaveRequestDTO = contactService.addContact(contactRequestDto);
        Assert.assertEquals(contactRequestDto.getFirstName(), contactSaveRequestDTO.getFirstName());
        Assert.assertEquals(contactRequestDto.getLastName(), contactSaveRequestDTO.getLastName());
        Assert.assertEquals(contactRequestDto.getProject(), contactSaveRequestDTO.getProject());
        Assert.assertEquals(contactRequestDto.getTitle(), contactSaveRequestDTO.getTitle());
    }
}
