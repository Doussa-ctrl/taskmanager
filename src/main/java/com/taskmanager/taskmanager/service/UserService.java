package com.taskmanager.taskmanager.service;



import java.util.Optional;

import org.springframework.stereotype.Service;

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

    public User findOrCreateUser(String name, String email, String picture) {

        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            return existingUser.get();
        }

        User newUser = new User();

        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPicture(picture);
        newUser.setProvider("GOOGLE");

        return userRepository.save(newUser);
    }
}

