package com.kms.seft203.service;

import com.kms.seft203.dto.RegisterRequest;
import com.kms.seft203.entity.User;
import com.kms.seft203.exception.EmailDuplicatedException;
import com.kms.seft203.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
/*
 * This class is defined for validating all fields of RegisterRequest class.
 * */
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testSave_whenSuccess_thenReturnRegisterRequest() throws EmailDuplicatedException {
        User user = new User(1,"nvdloc@apcs.vn", "11Qaz123@@", "Loc Nguyen");
        RegisterRequest userDto = new RegisterRequest();
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setFullName(user.getFullName());

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        RegisterRequest userResponse = userService.save(userDto);
        assertEquals("nvdloc@apcs.vn", userResponse.getEmail());
        assertEquals("11Qaz123@@", userResponse.getPassword());
        assertEquals("Loc Nguyen", userResponse.getFullName());
    }
}
