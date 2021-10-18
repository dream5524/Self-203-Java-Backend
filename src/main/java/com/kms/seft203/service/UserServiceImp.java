package com.kms.seft203.service;

import com.kms.seft203.dto.RegisterRequest;
import com.kms.seft203.entity.User;
import com.kms.seft203.exception.EmailDuplicatedException;
import com.kms.seft203.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class is implemented to handle to handle the service that belongs to the user, including:
 *      1/ save user
 *      ...
 */
@Service @Slf4j
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * This method is implemented to save a RegisterRequest received from Controller
     * to database. If the User is saved successfully, it will return a register request
     * DTO with the same fields as the received DTO. If the process failed, an exception
     * will be thrown and caught by CustomExceptionHandler
     *
     * @param: The DTO type of User (RegisterRequest)
     * @return RegisterRequest (a userDTO object) for controller. The front end will
     * receive this DTO object.
     *
     * @throws EmailDuplicatedException
     */
    @Override
    public RegisterRequest save(RegisterRequest userFromReq) throws EmailDuplicatedException {

        log.info("Saving user {} to database...", userFromReq.getEmail());
        String email = userFromReq.getEmail();

        Optional<User> userFromDb = userRepository.findByEmail(email);
        if (userFromDb.isPresent()) {
            log.error("Failed to save user {} to database! Duplicated exception", email);
            throw new EmailDuplicatedException("Duplicated email error! Register request for " + email + " rejected!");
        }
        User user = modelMapper.map(userFromReq, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return modelMapper.map(userRepository.save(user), RegisterRequest.class);
    }
}