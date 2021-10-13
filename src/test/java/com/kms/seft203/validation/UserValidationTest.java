package com.kms.seft203.validation;

import com.kms.seft203.controller.AuthApi;
import com.kms.seft203.controller.ControllerTest;
import com.kms.seft203.dto.RegisterRequest;
import com.kms.seft203.service.UserService;
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

@WebMvcTest(AuthApi.class)
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
/*
 * This class is defined for validating all fields of RegisterRequest class.
 * If all the fields are valid, return status 201 for client
 * Otherwise, return BadRequest with status 400
 * */
 class UserValidationTest extends ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @ParameterizedTest
    @CsvSource(value = {
            "1Qa,mohuyen,",
            "1Qa,mohuyen@gmail.com,Huyen Mo",
            "1Qaz@123Aqe,mohuyen@gmail.com,",
            "1Qaz@123Aqe,mohuyen@,Huyen Mo",
            "1Qaz@123Aqe,mohuyen524,H",
            "1Qa,mohuyen524@gmail.com,H",
            "1Qa,mohuyen524@,Huyen Mo"
    }, delimiter = ',')

   void whenFieldsInputAreInvalid_thenReturnStatusBadRequest(String password, String email, String fullName) throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(email);
        registerRequest.setFullName(fullName);
        registerRequest.setPassword(password);

        Mockito.when(userService.save(registerRequest)).thenReturn(registerRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .content(convertObjectToJsonString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource(value = {"1Qa123@@qe,mohuyen@gmail.com,Huyen Mo"}, delimiter = ',')

    void whenAllFieldsInputAreValid_thenReturnStatusIsCreated(String password, String email, String fullName) throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setPassword(password);
        registerRequest.setEmail(email);
        registerRequest.setFullName(fullName);

        Mockito.when(userService.save(registerRequest)).thenReturn(registerRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .content(convertObjectToJsonString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}

