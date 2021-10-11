package com.kms.seft203.validation;

import com.kms.seft203.controller.AuthApi;
import com.kms.seft203.controller.ControllerTest;
import com.kms.seft203.dto.RegisterRequest;
import com.kms.seft203.service.UserService;
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

@WebMvcTest(AuthApi.class)
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
/*
 * This class is defined for validating all fields of RegisterRequest class.
 * If all the fields are valid, return status 201 for client
 * Otherwise, return BadRequest with status 400
 * */
public class UserValidationTest extends ControllerTest {

    RegisterRequest registerRequest = new RegisterRequest();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void whenAllFieldsAreInValid_thenReturnStatus400() throws Exception {
        registerRequest.setPassword("1Qa");
        registerRequest.setEmail("mohuyen");
        registerRequest.setFullName("");

        Mockito.when(userService.save(Mockito.eq(registerRequest))).thenReturn(registerRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .content(convertObjectToJsonString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPasswordInputIsInValid_thenReturnStatus400() throws Exception {
        registerRequest.setPassword("1Qa");
        registerRequest.setEmail("mohuyen@gmail.com");
        registerRequest.setFullName("Huyen Mo");

        Mockito.when(userService.save(Mockito.eq(registerRequest))).thenReturn(registerRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .content(convertObjectToJsonString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenFullNameInputIsInValid_thenReturnStatus400() throws Exception {
        registerRequest.setPassword("1Qaz@123Aqe");
        registerRequest.setEmail("mohuyen@gmail.com");
        registerRequest.setFullName("");

        Mockito.when(userService.save(Mockito.eq(registerRequest))).thenReturn(registerRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .content(convertObjectToJsonString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenEmailInputIsInValid_thenReturnStatus400() throws Exception {
        registerRequest.setPassword("1Qaz@123Aqe");
        registerRequest.setEmail("mohuyen@");
        registerRequest.setFullName("Huyen Mo");

        Mockito.when(userService.save(Mockito.eq(registerRequest))).thenReturn(registerRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .content(convertObjectToJsonString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenEmailAndFullNameInputAreInValid_thenReturnStatus400() throws Exception {
        registerRequest.setPassword("1Qaz@123Aqe");
        registerRequest.setEmail("mohuyen524");
        registerRequest.setFullName("H");

        Mockito.when(userService.save(Mockito.eq(registerRequest))).thenReturn(registerRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .content(convertObjectToJsonString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPasswordAndFullNameInputAreInValid_thenReturnStatus400() throws Exception {
        registerRequest.setPassword("1Qa");
        registerRequest.setEmail("mohuyen@gmail.com");
        registerRequest.setFullName("H");

        Mockito.when(userService.save(Mockito.eq(registerRequest))).thenReturn(registerRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .content(convertObjectToJsonString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenEmailAndPasswordInputAreInValid_thenReturnStatus400() throws Exception {
        registerRequest.setPassword("1Qa");
        registerRequest.setEmail("mohuyen@");
        registerRequest.setFullName("Huyen Mo");

        Mockito.when(userService.save(Mockito.eq(registerRequest))).thenReturn(registerRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .content(convertObjectToJsonString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenAllFieldsAreValid_thenReturnStatus200() throws Exception {
        registerRequest.setPassword("1Qaz@123QAZ");
        registerRequest.setEmail("mohuyen@gmail.com");
        registerRequest.setFullName("Huyen Mo");

        Mockito.when(userService.save(Mockito.eq(registerRequest))).thenReturn(registerRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .content(convertObjectToJsonString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
