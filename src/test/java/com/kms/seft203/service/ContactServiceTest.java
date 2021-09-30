package com.kms.seft203.service;

import com.kms.seft203.dto.ContactRequestDTO;
import com.kms.seft203.repository.ContactRepository;
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

    @Test
    public void createContactServiceTest() {
        ContactRequestDTO contactRequestDTO = new ContactRequestDTO("huyenmo","Huyen", "Mo", "demo", "demo");
        Mockito.when(contactRepository.save(Mockito.any())).thenReturn(contactRequestDTO);

    }
}
