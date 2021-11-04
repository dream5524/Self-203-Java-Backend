package com.kms.seft203.service;

import com.kms.seft203.dto.RegisterRequest;
import com.kms.seft203.dto.RegisterResponse;
import com.kms.seft203.entity.User;
import com.kms.seft203.exception.EmailDuplicatedException;
import com.kms.seft203.exception.EmailNotFoundException;
import com.kms.seft203.exception.VerificationCodeInValidException;
import com.kms.seft203.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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

    private static int CODE_EXPIRATION_TIME = 15 * 60;

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
        user.setDateResetCode(user.getDateCreated());

        RegisterResponse registerResponse = modelMapper.map(userRepository.save(user), RegisterResponse.class);
        registerResponse.setSubject("Please click the link below to verify your registration." +
                " This code will expire in 15 minutes." + user.getVerificationCode());
        return registerResponse;
    }

    @Transactional
    @Override
    public Boolean verifyAccount(String verificationCode) throws VerificationCodeInValidException {
        Optional<User> userOptional = userRepository.findByVerificationCode(verificationCode);
        if (userOptional.isEmpty()) {
            throw new VerificationCodeInValidException("Verification Code is invalid.");
        }
        User userFromDb = userOptional.get();
        return checkValidationCode(userFromDb);
    }

    @Override
    public Boolean checkValidationCode(User user) {
        LocalDateTime expirationTime = user.getDateResetCode().plusSeconds(CODE_EXPIRATION_TIME);
        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isAfter(expirationTime)) {
            return false;
        } else {
            userRepository.enabledAccount(user.getId());
            return true;
        }
    }

    @Override
    public User resetCode(String email) throws EmailNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new EmailNotFoundException("Email " + email + " does not exist.");
        }
        User user = optionalUser.get();
        user.setDateResetCode(LocalDateTime.now());
        user.setVerificationCode(RandomString.make(50));
        userRepository.save(user);
        return user;
    }
}
