package com.kms.seft203.service;

import com.kms.seft203.dto.ContactRequestDTO;
import com.kms.seft203.entity.Contact;
import com.kms.seft203.repository.ContactRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
public class ContactServiceTest {
    @Autowired
    ContactService contactService;
    @MockBean
    ContactRepository contactRepository;

    @Test
    public void createContactServiceTest() {
        Contact contact = new Contact("Huyen", "Mo", "demo", "demo");
        ContactRequestDTO contactRequestDTO = new ContactRequestDTO("Huyen", "Mo", "demo", "demo");
        Mockito.when(contactRepository.save(Mockito.any())).thenReturn(contact);
        Assert.assertEquals(contact.getFirstName(), contactRequestDTO.getFirstName());
        Assert.assertEquals(contact.getLastName(), contactRequestDTO.getLastName());
        Assert.assertEquals(contact.getTitle(), contactRequestDTO.getTitle());
        Assert.assertEquals(contact.getProject(), contactRequestDTO.getProject());
    }
}
