package com.kms.seft203.service;

import com.kms.seft203.config.ApplicationPropertyConfig;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

/*
 * This class is defined for validating all fields of RegisterRequest class.
 * */
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:application-test.properties")
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationPropertyConfig propertyConfig;

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
        String expectedMessage = String.format("This verification code will valid in %d minutes", propertyConfig.getCodeExpirationMinute());
        String expectedActivationLink = propertyConfig.getActivationBaseUrl() + verificationCode;

        Assert.assertEquals(expectedMessage, registerResponse.getMessage());
        Assert.assertEquals(expectedActivationLink, registerResponse.getActivationLink());
    }

    @Test
    void testSave_whenEmailDuplicated_thenThrowEmailDuplicatedException() {
        String email = "nvdloc@apcs.vn";
        String password = "11Qaz123$%";
        String fullName = "Loc Nguyen";
        RegisterRequest registerRequest = new RegisterRequest(email, password, fullName);
        User user = new User(1, email, password, fullName);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Assert.assertThrows(EmailDuplicatedException.class, () -> {
            userService.save(registerRequest);
        });
    }

    @Test
    void testVerifyAccount_WhenCodeValidationTimeIsValid_ThenReturnTrue() throws VerificationCodeInValidException {
        String verificationCode = "12345@@SSdd";
        User user = new User(1, "mohuyen@gmail.com", "11Qaz123@@", "Mo Huyen");
        user.setDateResetCode(LocalDateTime.now());

        Mockito.when(userRepository.findByVerificationCode(verificationCode)).thenReturn(java.util.Optional.of(user));

        Boolean actualVerificationCode = userService.verifyAccount(verificationCode);

        Assert.assertEquals(true, actualVerificationCode);
    }

    @Test
    void testVerifyAccount_whenCodeValidationIsInvalid_thenThrowVerificationCodeInvalidException() {
        String verificationCode = "12345$%^Sdd";

        Mockito.when(userRepository.findByVerificationCode(verificationCode)).thenReturn(Optional.ofNullable(null));

        Assert.assertThrows(VerificationCodeInValidException.class, () -> {
            userService.verifyAccount(verificationCode);
        });
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
