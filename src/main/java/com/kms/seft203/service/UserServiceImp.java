package com.kms.seft203.service;

import com.kms.seft203.dto.RegisterRequest;
import com.kms.seft203.dto.RegisterResponse;
import com.kms.seft203.entity.User;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.exception.EmailDuplicatedException;
import com.kms.seft203.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;

/**
 * This class is implemented to handle to handle the service that belongs to the user, including:
 * 1/ save user
 * ...
 */
@Service
@Slf4j
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final long CODE_VALID_TIME = 5;

    /**
     * This method is implemented to save a RegisterRequest received from Controller
     * to database. If the User is saved successfully, it will return a register request
     * DTO with the same fields as the received DTO. If the process failed, an exception
     * will be thrown and caught by CustomExceptionHandler
     *
     * @return RegisterRequest (a userDTO object) for controller. The front end will
     * receive this DTO object.
     * @throws EmailDuplicatedException
     * @param: The DTO type of User (RegisterRequest)
     */
    @Override
    public RegisterResponse save(RegisterRequest userFromReq) throws EmailDuplicatedException {

        log.info("Saving user {} to database...", userFromReq.getEmail());
        String email = userFromReq.getEmail();

        Optional<User> userFromDb = userRepository.findByEmail(email);
        if (userFromDb.isPresent()) {
            log.error("Failed to save user {} to database! Duplicated exception", email);
            throw new EmailDuplicatedException("Duplicated email error! Register request for " + email + " rejected!");
        }
        User user = modelMapper.map(userFromReq, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        user.setVerificationCode(RandomString.make(50));
        user.setDateCreated(LocalDateTime.now());

        RegisterResponse registerResponse = modelMapper.map(userRepository.save(user), RegisterResponse.class);
        registerResponse.setSubject("Please click the link below to verify your registration." +
                " This code will expire in 15 minutes." + user.getVerificationCode());
        return registerResponse;
    }

    @Transactional
    @Override
    public void verifyAccount(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode).get();
        checkValidationCode(user);
    }

    @Override
    public Boolean checkValidationCode(User user) {
        Calendar calendar = Calendar.getInstance();
        if (user == null || user.getDateCreated().getSecond() + CODE_VALID_TIME < calendar.get(Calendar.SECOND)) {
            return false;
        } else {
            userRepository.enabledAccount(user.getId());
            return true;
        }
    }

    @Override
    public Boolean resetCode(String email) throws ContactNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new ContactNotFoundException("Do not find the given email: " + email + ". Email has not yet been registered.");
        }
        User user = userOptional.get();
    }

}