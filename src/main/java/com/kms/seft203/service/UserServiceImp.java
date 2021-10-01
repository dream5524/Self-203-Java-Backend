package com.kms.seft203.service;

import com.kms.seft203.dto.RegisterRequest;
import com.kms.seft203.entity.User;
import com.kms.seft203.exception.DuplicatedEmailException;
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
     * This method is implemented to
     *
     * @param: The DTO type of User (RegisterRequest)
     * @return RegisterRequest (a userDTO object) for controller. The front end will
     * receive this DTO object.
     *
     * @throws DuplicatedEmailException
     */
    @Override
    public RegisterRequest save(RegisterRequest userFromReq) throws DuplicatedEmailException {

        log.info("Saving user {} to database...", userFromReq.getEmail());
        String email = userFromReq.getEmail();

        Optional<User> userFromDb = userRepository.findByEmail(email);
        if (userFromDb.isPresent()) {
            log.error("Failed to save user {} to database! Duplicated exception", email);
            throw new DuplicatedEmailException("Duplicated email error! Register request for " + email + " rejected!");
        }
        User user = modelMapper.map(userFromReq, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, RegisterRequest.class);
    }
}