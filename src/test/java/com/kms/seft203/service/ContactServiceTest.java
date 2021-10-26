package com.kms.seft203.service;

import com.kms.seft203.dto.ContactRequestDto;
import com.kms.seft203.dto.ContactResponseDto;
import com.kms.seft203.entity.Contact;
import com.kms.seft203.entity.User;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.exception.EmailNotFoundException;
import com.kms.seft203.repository.ContactRepository;
import com.kms.seft203.repository.UserRepository;
import org.junit.Assert;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                                new User(1, "pheonc@gmail.com", "11Qaz123@@", "Nguyen Chi Pheo"),
                                "Tester",
                                "Implement API"))
                .collect(Collectors.toList());
        Mockito.when(contactRepository.findAll()).thenReturn(contactList);

        List<ContactResponseDto> expectedResponseList = Stream.of(
                        new ContactResponseDto("Nguyen Chi",
                                "Pheo",
                                new User(1, "pheonc@gmail.com", "11Qaz123@@", "Nguyen Chi Pheo"),
                                "Tester",
                                "Implement API"))
                .collect(Collectors.toList());

        List<ContactResponseDto> actualResponseList = contactService.getAllContact();

        Assert.assertEquals(1,actualResponseList.stream().count());
        Assert.assertEquals(expectedResponseList.get(0).getFirstName(), actualResponseList.get(0).getFirstName());
        Assert.assertEquals(expectedResponseList.get(0).getLastName(), actualResponseList.get(0).getLastName());
        Assert.assertEquals(expectedResponseList.get(0).getTitle(), actualResponseList.get(0).getTitle());
        Assert.assertEquals(expectedResponseList.get(0).getProject(), actualResponseList.get(0).getProject());
        Assert.assertEquals(expectedResponseList.get(0).getUser(), actualResponseList.get(0).getUser());

    }

    @Test
    void createContactServiceTest() throws EmailNotFoundException {
        User user = new User(1, "huyenmo@gmail.com", "21Qaz123@@", "Huyen Mo");
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
    @Test
    void updateByEmailTest_WhenSuccess_ThenReturnContactResponseDto() throws ContactNotFoundException {
        String email = "huyenmo@gmail.com";
        String firstName = "Huyen";
        String lastName = "Mo";
        String title = "Developer";
        String project = "Build Dashboard";
        User user = new User(1,"huyenmo@gmail.com","1QAZqaz@!","Huyen Mo");

        ContactRequestDto contactRequestDto = new ContactRequestDto("huyenmo@gmail.com",
                "Elisabeth", "II", "Queen", "Manager");

        Contact contactFromDb = new Contact(firstName, lastName, user, title, project);

        Mockito.when(contactRepository.findByEmail(email)).thenReturn(java.util.Optional.of(contactFromDb));
        contactFromDb.setFirstName(contactRequestDto.getFirstName());
        contactFromDb.setLastName(contactRequestDto.getLastName());
        contactFromDb.setTitle(contactRequestDto.getTitle());
        contactFromDb.setProject(contactRequestDto.getProject());
        Mockito.when(contactRepository.save(contactFromDb)).thenReturn(contactFromDb);

        ContactResponseDto contactResponseDto = contactService.updateByEmail(contactRequestDto);

        Assert.assertEquals(contactFromDb.getFirstName(), contactResponseDto.getFirstName());
        Assert.assertEquals(contactFromDb.getLastName(), contactResponseDto.getLastName());
        Assert.assertEquals(contactFromDb.getTitle(), contactResponseDto.getTitle());
        Assert.assertEquals(contactFromDb.getProject(), contactResponseDto.getProject());
    }

    @Test
    void updateByEmailTest_WhenNotFoundContact_ThenReturnContactNotFoundException() throws Exception {
        String email = "huyenmo@gmail.com";
        String firstName = "Huyen";
        String lastName = "Mo";
        String title = "Developer";
        String project = "Build Dashboard";
        String message = "Email "+email+" does not exist.";

        ContactRequestDto contactRequestDto = new ContactRequestDto(email, firstName, lastName, title, project);

        Exception exception = assertThrows(ContactNotFoundException.class, () ->
                Mockito.when(contactService.updateByEmail(contactRequestDto)));

        Assert.assertEquals(message, exception.getMessage());
    }
}
