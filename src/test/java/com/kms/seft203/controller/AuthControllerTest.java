package com.kms.seft203.controller;

import com.kms.seft203.dto.RegisterRequest;
import com.kms.seft203.dto.RegisterResponse;
import com.kms.seft203.exception.EmailDuplicatedException;
import com.kms.seft203.service.EmailService;
import com.kms.seft203.service.UserService;
import org.hamcrest.core.IsNull;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class is implemented to test the controller layer - AuthApi class
 *
 * Two unit testing is designed, including the successful case and the fail
 * situation when the email is duplicated
 */
@WebMvcTest(AuthApi.class)
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
class AuthControllerTest extends ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private EmailService emailService;

    @Test
    void testRegister_whenSuccess_thenReturnRegisterRequestFormat() throws Exception {

        String email = "nvdloc@apcs.vn";
        String password = "11Qwaz#()(4321A";
        String fullName = "Loc Nguyen";
        Boolean activation = false;
        RegisterRequest mockUserDto = new RegisterRequest(email, password, fullName);
        RegisterResponse responseUserDto = new RegisterResponse(email, fullName, activation);

        Mockito.when(userService.save(mockUserDto)).thenReturn(responseUserDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .content(convertObjectToJsonString(mockUserDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("nvdloc@apcs.vn"))
                .andExpect(jsonPath("$.fullName").value("Loc Nguyen"))
                .andExpect(jsonPath("$.activation").value(activation));
    }

    @Test
    void testRegister_whenFailed_thenReturnEmailDuplicationError() throws Exception {
        RegisterRequest mockUserDto = new RegisterRequest("huyenmo@gmail.com", "11Qaz123@@", "Loc");
        String message = "Duplicated error! Email is already used!";

        Mockito.when(userService.save(mockUserDto)).thenThrow(new EmailDuplicatedException(message));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .content(convertObjectToJsonString(mockUserDto))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messages[0]").value(message));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1Qa,mohuyen,",
            "1Qa,mohuyen@gmail.com,Huyen Mo",
            "1Qaz@123Aqe,mohuyen@gmail.com,",
            "1Qaz@123Aqe,mohuyen@,Huyen Mo",
            "1Qaz@123Aqe,mohuyen524,H",
            "1Qa,mohuyen524@gmail.com,H",
            "1Qa,mohuyen524@,Huyen Mo",
            ",,,",
            "1Qaz@123Aqe,,Huyen Mo"
    }, delimiter = ',')
    void testRegister_whenFieldsInputAreInvalid_thenReturnStatusBadRequest(String password, String email, String fullName) throws Exception {
        RegisterRequest registerRequest = new RegisterRequest(email, password, fullName);
        Boolean activation = false;
        RegisterResponse registerResponse = new RegisterResponse(email, fullName, activation);
        Mockito.when(userService.save(registerRequest)).thenReturn(registerResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .content(convertObjectToJsonString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegister_whenAllFieldsInputAreValid_thenReturnStatusIsCreated() throws Exception {
        String email = "mohuyen@gmail.com";
        String password = "1Qa123@@qe";
        String fullName = "Huyen Mo";
        Boolean activation = false;

        RegisterRequest registerRequest = new RegisterRequest(email, password, fullName);
        RegisterResponse registerResponse = new RegisterResponse(email, fullName, activation);

        Mockito.when(userService.save(registerRequest)).thenReturn(registerResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .content(convertObjectToJsonString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
