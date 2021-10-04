package com.kms.seft203.service;

import com.kms.seft203.dto.ContactRequestDTO;
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
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
public class ContactServiceTest {
    @Autowired
    private ContactService contactService;
    @MockBean
    private ContactRepository contactRepository;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void createContactServiceTest() throws EmailNotFoundException {
        User user = new User(1, "huyenmo@gmail.com", "2", "Huyen Mo");
        Contact contact = new Contact("Huyen", "Mo", "demo", "demo");
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(java.util.Optional.of(user));

        Mockito.when(contactRepository.save(Mockito.any(Contact.class))).thenReturn(contact);
        ContactRequestDTO contactRequestDTO = new ContactRequestDTO("huyenmo@gmail.com", "Huyen", "Mo", "demo", "demo");
        ContactRequestDTO contactSaveRequestDTO = contactService.addContact(contactRequestDTO);
        Assert.assertEquals(contactRequestDTO.getFirstName(), contactSaveRequestDTO.getFirstName());
        Assert.assertEquals(contactRequestDTO.getLastName(), contactSaveRequestDTO.getLastName());
        Assert.assertEquals(contactRequestDTO.getProject(), contactSaveRequestDTO.getProject());
        Assert.assertEquals(contactRequestDTO.getTitle(), contactSaveRequestDTO.getTitle());
    }
}
