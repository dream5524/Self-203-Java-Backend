package com.kms.seft203.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kms.seft203.controller.ContactApi;
import com.kms.seft203.controller.ControllerTest;
import com.kms.seft203.dto.ContactRequestDto;
import com.kms.seft203.exception.EmailNotFoundException;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactApi.class)
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
        /*
         * This class is defined for validating all fields of ContactRequestDto class.
         * If all the fields are valid, return status 200 for client
         * Otherwise, return BadRequest with status 400
         * */

class ContactValidationTest extends ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @Test
    void whenAllFieldsInputAreInValid_thenReturnStatusBadRequest() throws Exception {
        ContactRequestDto contactRequestDto = new ContactRequestDto();
        contactRequestDto.setEmail("mohuyen");
        contactRequestDto.setFirstName("H");
        contactRequestDto.setLastName("");
        contactRequestDto.setTitle("");
        contactRequestDto.setProject("");

        Mockito.when(contactService.addContact(Mockito.any())).thenReturn(contactRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .content(convertObjectToJsonString(contactRequestDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenEmailInputIsInValid_thenReturnStatusBadRequest() throws Exception {
        ContactRequestDto contactRequestDto = new ContactRequestDto();
        contactRequestDto.setEmail("mohuyen");
        contactRequestDto.setFirstName("Huyen");
        contactRequestDto.setLastName("Mo");
        contactRequestDto.setTitle("Tester");
        contactRequestDto.setProject("Build Dashboard");

        Mockito.when(contactService.addContact(Mockito.any())).thenReturn(contactRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .content(convertObjectToJsonString(contactRequestDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenFirstNameInputIsInValid_thenReturnStatusBadRequest() throws Exception {
        ContactRequestDto contactRequestDto = new ContactRequestDto();
        contactRequestDto.setEmail("mohuyen@gmail.com");
        contactRequestDto.setFirstName("H");
        contactRequestDto.setLastName("Mo");
        contactRequestDto.setTitle("Tester");
        contactRequestDto.setProject("Build Dashboard");

        Mockito.when(contactService.addContact(Mockito.any())).thenReturn(contactRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .content(convertObjectToJsonString(contactRequestDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenLastNameInputIsInValid_thenReturnStatusBadRequest() throws Exception {
        ContactRequestDto contactRequestDto = new ContactRequestDto();
        contactRequestDto.setEmail("mohuyen@gmail.com");
        contactRequestDto.setFirstName("Huyen");
        contactRequestDto.setLastName("");
        contactRequestDto.setTitle("Tester");
        contactRequestDto.setProject("Build Dashboard");

        Mockito.when(contactService.addContact(Mockito.any())).thenReturn(contactRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .content(convertObjectToJsonString(contactRequestDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenTitleInputIsInValid_thenReturnStatusBadRequest() throws Exception {
        ContactRequestDto contactRequestDto = new ContactRequestDto();
        contactRequestDto.setEmail("mohuyenWgmail.com");
        contactRequestDto.setFirstName("Huyen");
        contactRequestDto.setLastName("Mo");
        contactRequestDto.setTitle("");
        contactRequestDto.setProject("Build Dashboard");

        Mockito.when(contactService.addContact(Mockito.any())).thenReturn(contactRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .content(convertObjectToJsonString(contactRequestDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenProjectInputIsInValid_thenReturnStatusBadRequest() throws Exception {
        ContactRequestDto contactRequestDto = new ContactRequestDto();
        contactRequestDto.setEmail("mohuyen@gmail.com");
        contactRequestDto.setFirstName("Huyen");
        contactRequestDto.setLastName("Mo");
        contactRequestDto.setTitle("Tester");
        contactRequestDto.setProject("");

        Mockito.when(contactService.addContact(Mockito.any())).thenReturn(contactRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .content(convertObjectToJsonString(contactRequestDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenEmailAndFirstNameInputAreInValid_thenReturnStatusBadRequest() throws Exception {
        ContactRequestDto contactRequestDto = new ContactRequestDto();
        contactRequestDto.setEmail("mohuyen");
        contactRequestDto.setFirstName("");
        contactRequestDto.setLastName("Mo");
        contactRequestDto.setTitle("Tester");
        contactRequestDto.setProject("Build Dashboard");

        Mockito.when(contactService.addContact(Mockito.any())).thenReturn(contactRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .content(convertObjectToJsonString(contactRequestDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenFirstNameAndLastNameAreInValid_thenReturnStatusBadRequest() throws Exception {
        ContactRequestDto contactRequestDto = new ContactRequestDto();
        contactRequestDto.setEmail("mohuyen@gmail.com");
        contactRequestDto.setFirstName("");
        contactRequestDto.setLastName("");
        contactRequestDto.setTitle("Tester");
        contactRequestDto.setProject("Build Dashboard");

        Mockito.when(contactService.addContact(Mockito.any())).thenReturn(contactRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .content(convertObjectToJsonString(contactRequestDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenLastNameAndTitleAreInValid_thenReturnStatusBadRequest() throws Exception {
        ContactRequestDto contactRequestDto = new ContactRequestDto();
        contactRequestDto.setEmail("mohuyen@gmail.com");
        contactRequestDto.setFirstName("Huyen");
        contactRequestDto.setLastName("");
        contactRequestDto.setTitle("");
        contactRequestDto.setProject("Build Dashboard");

        Mockito.when(contactService.addContact(Mockito.any())).thenReturn(contactRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .content(convertObjectToJsonString(contactRequestDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenProjectAndTitleAreInValid_thenReturnStatusBadRequest() throws Exception {
        ContactRequestDto contactRequestDto = new ContactRequestDto();
        contactRequestDto.setEmail("mohuyen@gmail.com");
        contactRequestDto.setFirstName("Huyen");
        contactRequestDto.setLastName("Mo");
        contactRequestDto.setTitle("");
        contactRequestDto.setProject("");

        Mockito.when(contactService.addContact(Mockito.any())).thenReturn(contactRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .content(convertObjectToJsonString(contactRequestDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenProjectAndEmailAreInValid_thenReturnStatusBadRequest() throws Exception {
        ContactRequestDto contactRequestDto = new ContactRequestDto();
        contactRequestDto.setEmail("@gmail.com");
        contactRequestDto.setFirstName("Huyen");
        contactRequestDto.setLastName("Mo");
        contactRequestDto.setTitle("Tester");
        contactRequestDto.setProject("");

        Mockito.when(contactService.addContact(Mockito.any())).thenReturn(contactRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .content(convertObjectToJsonString(contactRequestDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenEmailAndFirstNameAndLastNameAreInValid_thenReturnStatusBadRequest() throws Exception {
        ContactRequestDto contactRequestDto = new ContactRequestDto();
        contactRequestDto.setEmail("@gmail.com");
        contactRequestDto.setFirstName("");
        contactRequestDto.setLastName("");
        contactRequestDto.setTitle("Tester");
        contactRequestDto.setProject("Build Dashboard");

        Mockito.when(contactService.addContact(Mockito.any())).thenReturn(contactRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .content(convertObjectToJsonString(contactRequestDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenTitleAndFirstNameAndLastNameAreInValid_thenReturnStatusBadRequest() throws Exception {
        ContactRequestDto contactRequestDto = new ContactRequestDto();
        contactRequestDto.setEmail("mohuyen@gmail.com");
        contactRequestDto.setFirstName("");
        contactRequestDto.setLastName("");
        contactRequestDto.setTitle("");
        contactRequestDto.setProject("Build Dashboard");

        Mockito.when(contactService.addContact(Mockito.any())).thenReturn(contactRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .content(convertObjectToJsonString(contactRequestDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenProjectAndTitleAndLastNameAreInValid_thenReturnStatusBadRequest() throws Exception {
        ContactRequestDto contactRequestDto = new ContactRequestDto();
        contactRequestDto.setEmail("@gmail.com");
        contactRequestDto.setFirstName("Huyen");
        contactRequestDto.setLastName("");
        contactRequestDto.setTitle("");
        contactRequestDto.setProject("");

        Mockito.when(contactService.addContact(Mockito.any())).thenReturn(contactRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .content(convertObjectToJsonString(contactRequestDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenProjectAndEmailAndFirstNameAreInValid_thenReturnStatusBadRequest() throws Exception {
        ContactRequestDto contactRequestDto = new ContactRequestDto();
        contactRequestDto.setEmail("@gmail.com");
        contactRequestDto.setFirstName("");
        contactRequestDto.setLastName("Mo");
        contactRequestDto.setTitle("Tester");
        contactRequestDto.setProject("");

        Mockito.when(contactService.addContact(Mockito.any())).thenReturn(contactRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .content(convertObjectToJsonString(contactRequestDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenAllFieldsInputAreValid_thenReturnStatusIsCreated() throws Exception {
        ContactRequestDto contactRequestDto = new ContactRequestDto();
        contactRequestDto.setEmail("mohuyen@gmail.com");
        contactRequestDto.setFirstName("Huyen");
        contactRequestDto.setLastName("Mo");
        contactRequestDto.setTitle("Tester");
        contactRequestDto.setProject("Build Dashboard");

        Mockito.when(contactService.addContact(Mockito.any())).thenReturn(contactRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .content(convertObjectToJsonString(contactRequestDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
