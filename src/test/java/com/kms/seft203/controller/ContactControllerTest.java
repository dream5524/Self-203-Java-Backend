package com.kms.seft203.controller;

import com.kms.seft203.dto.ContactRequestDto;
import com.kms.seft203.dto.ContactResponseDto;
import com.kms.seft203.entity.User;
import com.kms.seft203.service.ContactService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
This class is defined for testing create new contact, get all contact methods
* */
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@WebMvcTest(ContactApi.class)
class ContactControllerTest extends ControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ContactService contactService;

    @Test
    void getAllContactTest() throws Exception {
        List<ContactResponseDto> listContactResponseDto = new ArrayList<ContactResponseDto>();
        listContactResponseDto.add(new ContactResponseDto("Nguyen Van", "Teo", new User(1, "teonv@gmail.com", "1", "Nguyen Van Teo"), "Tester", "Implement API"));
        listContactResponseDto.add(new ContactResponseDto("Tran Thi", "No", new User(2, "nott@gmail.com", "1", "Tran Thi No"), "Business Analyst", "Implement API"));
        Mockito.when(contactService.getAllContact()).thenReturn(listContactResponseDto);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/contacts")
                        .content(convertObjectListToJsonString(Arrays.asList(listContactResponseDto.toArray())))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void createContactTest() throws Exception {
        ContactRequestDto contactRequestDTO = new ContactRequestDto("huyenmo@gmail.com", "Huyen", "Mo", "title demo", "project demo");
        Mockito.when(contactService.addContact(Mockito.any())).thenReturn(contactRequestDTO);
        // Execute the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .content(convertObjectToJsonString(contactRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(contactRequestDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(contactRequestDTO.getLastName()))
                .andExpect(jsonPath("$.title").value(contactRequestDTO.getTitle()))
                .andExpect(jsonPath("$.project").value(contactRequestDTO.getProject()));
    }
}
