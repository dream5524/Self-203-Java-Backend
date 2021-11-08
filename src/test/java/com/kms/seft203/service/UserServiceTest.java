package com.kms.seft203.service;

import com.kms.seft203.dto.RegisterRequest;
import com.kms.seft203.dto.RegisterResponse;
import com.kms.seft203.entity.User;
import com.kms.seft203.exception.EmailDuplicatedException;
import com.kms.seft203.exception.EmailNotFoundException;
import com.kms.seft203.exception.VerificationCodeInValidException;
import com.kms.seft203.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

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
    void testSave_whenSuccess_thenReturnRegisterResponse() throws EmailDuplicatedException {
        String email = "nvdloc@apcs.vn";
        String password = "11Qaz123$%";
        String fullName = "Loc Nguyen";
        Boolean enabled = false;
        String verificationCode = "123456789";
        LocalDateTime dateCreated = LocalDateTime.now();
        LocalDateTime dateResetCode = dateCreated;
        RegisterRequest registerRequest = new RegisterRequest(email, password, fullName);
        User user = new User(null, email, password, fullName, enabled, verificationCode, dateCreated, dateResetCode);

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        RegisterResponse registerResponse = userService.save(registerRequest);
        String expectedMessage = String.format("This verification code will valid in 15 minutes");
        String expectedActivationLink = "http://localhost:8000/auth/verify?code=" + verificationCode;

        Assert.assertEquals(expectedMessage, registerResponse.getMessage());
        Assert.assertEquals(expectedActivationLink, registerResponse.getActivationLink());
    }

    @Test
    void checkVerificationAccount_WhenCodeValidationTimeIsValid_ThenReturnTrue() throws VerificationCodeInValidException {
        String verificationCode = "12345@@SSdd";
        User user = new User(1, "mohuyen@gmail.com", "11Qaz123@@", "Mo Huyen");
        user.setDateResetCode(LocalDateTime.now());

        Mockito.when(userRepository.findByVerificationCode(verificationCode)).thenReturn(java.util.Optional.of(user));

        Boolean actualVerificationCode = userService.verifyAccount(verificationCode);

        Assert.assertEquals(true, actualVerificationCode);
    }
    @Test
    void testResetCode_WhenEmailIsFound_ThenReturnUser() throws EmailNotFoundException {
        User expectUser = new User(1, "mohuyen@gmail.com", "11Qaz123@@", "Mo Huyen");

        Mockito.when(userRepository.findByEmail(expectUser.getEmail())).thenReturn(Optional.of(expectUser));

        expectUser.setDateResetCode(LocalDateTime.now());
        expectUser.setVerificationCode(RandomString.make(50));
        Mockito.when(userRepository.save(expectUser)).thenReturn(expectUser);

        User actualUser = userService.resetCode("mohuyen@gmail.com");

        Assert.assertNotNull(actualUser);
        Assert.assertEquals(expectUser.getEmail(), actualUser.getEmail());
        Assert.assertEquals(expectUser.getPassword(), actualUser.getPassword());
        Assert.assertEquals(expectUser.getFullName(), actualUser.getFullName());
        Assert.assertEquals(expectUser.getVerificationCode(), actualUser.getVerificationCode());
        Assert.assertEquals(expectUser.getDateResetCode(), actualUser.getDateResetCode());
    }
}
