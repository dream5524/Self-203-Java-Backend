package com.kms.seft203.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kms.seft203.entity.Contact;
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
public class ContactControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ContactServiceImp contactService;

    @Test
    public void createContactTest() throws Exception {
        Contact contact = new Contact();
        contact.setFirstName("Huyen");
        contact.setLastName("Mo");
        contact.setTitle("title demo");
        contact.setProject("project demo");
        // Execute the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/contacts")
                        .content(convertJsonString(contact))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());
    }
    //convert object to json
    public static String convertJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
