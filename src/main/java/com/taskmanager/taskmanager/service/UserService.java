package com.taskmanager.taskmanager.service;



import java.util.Optional;

import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Service;

import com.taskmanager.taskmanager.enums.AccountStatus;
import com.taskmanager.taskmanager.model.User;
import com.taskmanager.taskmanager.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findOrCreateUser(
        String name,
        String email,
        String picture) {

    Optional<User> existing =
            userRepository.findByEmail(email);

    if (existing.isPresent()) {

        User user = existing.get();

        if (user.getAccountStatus() == AccountStatus.INACTIVE) {
            throw new DisabledException(
                "Your account has been deactivated by admin."
            );
        }

        return user;
    }

    User newUser = new User();

    newUser.setName(name);
    newUser.setEmail(email);
    newUser.setPicture(picture);
    newUser.setProvider("GOOGLE");

    return userRepository.save(newUser);
}
    
}

