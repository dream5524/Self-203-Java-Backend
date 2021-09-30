package com.kms.seft203.controller;

import com.kms.seft203.converter.ConvertObjectToJsonString;
import com.kms.seft203.dto.ContactRequestDTO;
import com.kms.seft203.service.ContactServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/*
This class is defined for testing create new contact method
* */
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@WebMvcTest(ContactApi.class)
public class ContactControllerTest extends ConvertObjectToJsonString {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ContactServiceImp contactService;

    @Test
    public void createContactTest() throws Exception {
        ContactRequestDTO ContactRequestDTO = new ContactRequestDTO("huyenmo@gmail.com","Huyen", "Mo", "title demo", "project demo");
        // Execute the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .content(convertObjectToJsonString(ContactRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
