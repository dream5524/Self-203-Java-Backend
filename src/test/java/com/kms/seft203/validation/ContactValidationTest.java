package com.kms.seft203.validation;

import com.kms.seft203.controller.ContactApi;
import com.kms.seft203.controller.ControllerTest;
import com.kms.seft203.dto.ContactRequestDto;
import com.kms.seft203.service.ContactService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
         * If all the fields are valid, return status is created for client
         * Otherwise, return BadRequest with status Bad Request
         * */

class ContactValidationTest extends ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @ParameterizedTest
    @CsvSource(value = {
            "mohuyen,H,,,,",
            "mohuyen,Huyen,Mo,Tester,Build Dashboard",
            "mohuyen@gmail.com,H,Mo,Tester,Build Dashboard",
            "mohuyen@gmail.com,Huyen,,Tester,Build Dashboard",
            "mohuyenWgmail.com,Huyen,Mo,,Build Dashboard",
            "mohuyen@gmail.com,Huyen,Mo,Tester,,",
            "mohuyen,,Mo,Tester,Build Dashboard",
            "mohuyen@gmail.com,,,Tester,Build Dashboard",
            "mohuyen@gmail.com,Huyen,,,Build Dashboard",
            "mohuyen@gmail.com,Huyen,Mo,,,",
            "@gmail.com,Huyen,Mo,Tester,,",
            "@gmail.com,,,Tester,Build Dashboard",
            "mohuyen@gmail.com,,,,Build Dashboard",
            "@gmail.com,Huyen,,,,",
            "@gmail.com,,Mo,Tester,"
    }, delimiter = ',')
    void whenFieldsInputAreInvalid_thenReturnStatusBadRequest(String email, String firstName, String lastName, String title, String project) throws Exception {
        ContactRequestDto contactRequestDto = new ContactRequestDto();
        contactRequestDto.setEmail(email);
        contactRequestDto.setFirstName(firstName);
        contactRequestDto.setLastName(lastName);
        contactRequestDto.setTitle(title);
        contactRequestDto.setProject(project);

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
