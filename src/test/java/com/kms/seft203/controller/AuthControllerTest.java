package com.kms.seft203.controller;

import com.kms.seft203.config.RabbitmqConfig;
import com.kms.seft203.dto.EmailActivationDto;
import com.kms.seft203.dto.RegisterRequest;
import com.kms.seft203.dto.RegisterResponse;
import com.kms.seft203.entity.User;
import com.kms.seft203.exception.EmailDuplicatedException;
import com.kms.seft203.exception.EmailNotFoundException;
import com.kms.seft203.service.EmailService;
import com.kms.seft203.service.MessageSender;
import com.kms.seft203.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class is implemented to test the controller layer - AuthApi class
 * <p>
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

    @MockBean
    private MessageSender messageSender;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private RabbitmqConfig rabbitmqConfig;

    @Test

    void testRegister_whenSuccess_thenReturnStatusIsCreated () throws Exception {
        String message = "Please click the link below to verify your registration." +
                "This code will expire in 15 minutes.";
        String email = "nvdloc@apcs.vn";
        String password = "123Abcd!#ab";
        String fullName = "Loc Nguyen";
        String activationLink = "http://localhost:8000/verify?code=12345";
        RegisterRequest registerRequest = new RegisterRequest(email, password, fullName);
        RegisterResponse registerResponse = new RegisterResponse(message, activationLink);
        EmailActivationDto emailActivationDto = new EmailActivationDto();
        emailActivationDto.setEmail(registerRequest.getEmail());
        emailActivationDto.setActivationLink(registerResponse.getActivationLink());

        Mockito.when(userService.save(any())).thenReturn(registerResponse);
        Mockito.doNothing().when(messageSender).sendMessageToQueue(any(RabbitTemplate.class)
                , eq("exchangeName"), eq("routingKeyName"), eq(emailActivationDto));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .content(convertObjectToJsonString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(message))
                .andExpect(jsonPath("$.activationLink").value(activationLink));

        verify(messageSender, times(1)).sendMessageToQueue(any(RabbitTemplate.class)
                , Mockito.any(), Mockito.any(), eq(emailActivationDto));
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

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .content(convertObjectToJsonString(registerRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testVerificationAccount_WhenCodeIsValid_ThenReturnSuccessMessage() throws Exception {
        String verificationCode = "12345@@SSdd";
        String messageResponse = "Account was verified successfully !";

        Mockito.when(userService.verifyAccount(verificationCode)).thenReturn(true);


        mockMvc.perform(MockMvcRequestBuilders.get("/auth/verify")
                        .param("code", verificationCode))
                .andExpect(status().isOk())
                .andExpect(content().string(messageResponse));
    }

    @Test
    void testVerificationAccount_WhenCodeIsExpired_ThenReturnFailMessage() throws Exception {
        String verificationCode = "12345@@SSdd";
        String messageResponse = "Verification failed ! Code expired.";

        Mockito.when(userService.verifyAccount(verificationCode)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/auth/verify")
                        .param("code", verificationCode))
                .andExpect(status().isGone())
                .andExpect(content().string(messageResponse));
    }

    @Test
    void testResetCode_WhenEmailIsFound_ThenReturnCodeSuccessfullyResetMessage() throws Exception {
        User user = new User(1, "mohuyen@gmail.com", "11Qaz123@@", "Mo Huyen");

        Mockito.when(userService.resetCode(user.getEmail())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/auth/reset-code")
                        .param("email", user.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().string("Code successfully reset ! Check your email again to confirm your account."));
    }

    @Test
    void testResetCode_WhenEmailIsNotFound_ThenReturnStatusNotFound() throws Exception {
        User user = new User(1, "mohuyen@gmail.com", "11Qaz123@@", "Mo Huyen");
        String messageResponse = "Email" + user.getEmail() + " does not exist.";

        Mockito.when(userService.resetCode(user.getEmail())).thenThrow(new EmailNotFoundException(messageResponse));

        mockMvc.perform(MockMvcRequestBuilders.get("/auth/reset-code")
                        .param("email", user.getEmail()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.messages[0]").value(messageResponse));
    }
}
