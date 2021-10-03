package com.kms.seft203.controller;

import com.kms.seft203.dto.RegisterRequest;
import com.kms.seft203.exception.DuplicatedEmailException;
import com.kms.seft203.service.UserService;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(AuthApi.class)
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
class AuthControllerTest extends ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    @Test
    void registerTest() throws Exception {
        RegisterRequest mockUserDto =
                new RegisterRequest("nvdloc@apcs.vn", "1", "Loc Nguyen");

        // Test for successful case
        Mockito.when(userService.save(mockUserDto)).thenReturn(mockUserDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                .content(convertObjectToJsonString(mockUserDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("nvdloc@apcs.vn"))
                .andExpect(jsonPath("$.password").value(IsNull.nullValue()))
                .andExpect(jsonPath("$.fullName").value("Loc Nguyen"));

        // Test for exception case (email duplication)
        RegisterRequest failedMockUserDto = new RegisterRequest("a@gmail.com", "1", "A");
        String message = "Duplicated error! Email is already used!";
        DuplicatedEmailException exception = new DuplicatedEmailException(message);
        Mockito.when(userService.save(failedMockUserDto)).thenThrow(exception);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                .content(convertObjectToJsonString(failedMockUserDto))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.NOT_ACCEPTABLE.value()))
                .andExpect(jsonPath("$.message").value(message));
    }
}
