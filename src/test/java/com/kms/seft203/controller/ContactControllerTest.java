package com.kms.seft203.controller;

import com.kms.seft203.dto.ContactRequestDto;
import com.kms.seft203.dto.ContactResponseDto;
import com.kms.seft203.entity.User;
import com.kms.seft203.service.ContactService;
import org.junit.Assert;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        List<ContactResponseDto> listContactResponseDto = Stream.of(
                        new ContactResponseDto("Nguyen Van",
                                "Teo",
                                new User(1, "teonv@gmail.com", "1Qaz123@@", "Nguyen Van Teo"),
                                "Tester",
                                "Implement API"))
                .collect(Collectors.toList());

        Mockito.when(contactService.getAllContact()).thenReturn(listContactResponseDto);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/contacts")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String expectedJsonList = this.convertObjectToJsonString(listContactResponseDto);
        String actualJsonList = mvcResult.getResponse().getContentAsString();

        Assert.assertEquals(expectedJsonList, actualJsonList);
    }

    @Test
    void createContactTest() throws Exception {
        ContactRequestDto contactRequestDto = new ContactRequestDto("huyenmo@gmail.com", "Huyen", "Mo", "title demo", "project demo");

        Mockito.when(contactService.addContact(Mockito.any())).thenReturn(contactRequestDto);

        // Execute the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/contacts")
                        .content(convertObjectToJsonString(contactRequestDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(contactRequestDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(contactRequestDto.getLastName()))
                .andExpect(jsonPath("$.title").value(contactRequestDto.getTitle()))
                .andExpect(jsonPath("$.project").value(contactRequestDto.getProject()));
    }

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
    @Test
    void updateByEmailTest_WhenSuccess_ThenReturnStatusOK() throws Exception {
        String email = "huyenmo@gmail.com";
        String firstName = "Huyen";
        String lastName = "Mo";
        String title = "Developer";
        String project = "Build Dashboard";
        User user = new User(1,"huyenmo@gmail.com","1QAZqaz@!","Huyen Mo");

        ContactResponseDto contactResponseDto = new ContactResponseDto(firstName, lastName, user, title, project);
        ContactRequestDto contactRequestDto = new ContactRequestDto(email, firstName, lastName, title, project);

        Mockito.when(contactService.updateByEmail(contactRequestDto)).thenReturn(contactResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/contacts")
                .content(convertObjectToJsonString(contactRequestDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("The information were successful updated !"));
    }
}
