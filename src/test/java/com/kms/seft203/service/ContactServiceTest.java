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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @ParameterizedTest
    @CsvSource(value = {
            "1,,,,,,",
            ",Huyen Mo,,,",
            ",,Tester,,",
            "1,Huyen Mo,,,",
            ",Huyen Mo, Tester,,",
            "1, ,Tester,,",
            "1,Huyen Mo,Tester,,",
            ",,,,,",
            ",,,1,3"
    }, delimiter = ',')
    void getAllByFilterTest_WhenSuccess_thenReturnContactResponseDtoList(Integer id, String fullName, String title, Integer page, Integer size) {
        User user = new User(1, "pheonc@gmail.com", "11Qaz123@@", "Nguyen Chi Pheo", true);

        Page<Contact> contactPage = new PageImpl<>(Arrays.asList(
                new Contact("Nguyen Chi", "Pheo", user, "Tester", "Build dashboard")));

        Mockito.when(contactRepository.findAllByInputField(
                        Mockito.eq(id), Mockito.eq(fullName), Mockito.eq(title), Mockito.any(Pageable.class)))
                .thenReturn(contactPage);

        List<ContactResponseDto> actualResponseList = contactService.getAllByFilter(id, fullName, title, 1, 3);

        Assert.assertEquals(1, actualResponseList.stream().count());
        Assert.assertEquals("Nguyen Chi", actualResponseList.get(0).getFirstName());
        Assert.assertEquals("Pheo", actualResponseList.get(0).getLastName());
        Assert.assertEquals("Tester", actualResponseList.get(0).getTitle());
        Assert.assertEquals("Build dashboard", actualResponseList.get(0).getProject());
        Assert.assertEquals(user, actualResponseList.get(0).getUser());
    }

    @Test
    void createContactServiceTest_WhenSuccess_ThenReturnContactRequestDto() throws EmailNotFoundException {
        User user = new User(1, "huyenmo@gmail.com", "21Qaz123@@", "Huyen Mo", true);
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
        User user = new User(1, "huyenmo@gmail.com", "1QAZqaz@!", "Huyen Mo", true);

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
    void updateByEmailTest_WhenNotFoundContactFromDb_ThenReturnContactNotFoundException() throws Exception {
        String email = "huyenmo@gmail.com";
        String firstName = "Huyen";
        String lastName = "Mo";
        String title = "Developer";
        String project = "Build Dashboard";
        String message = "Email " + email + " does not exist";

        ContactRequestDto contactRequestDto = new ContactRequestDto(email, firstName, lastName, title, project);

        Exception exception = assertThrows(ContactNotFoundException.class, () ->
                Mockito.when(contactService.updateByEmail(contactRequestDto)));

        Assert.assertEquals(message, exception.getMessage());
    }
}
