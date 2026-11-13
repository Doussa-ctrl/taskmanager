package com.taskmanager.taskmanager.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanager.taskmanager.enums.AccountStatus;
import com.taskmanager.taskmanager.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
  long countByAccountStatus(AccountStatus accountStatus);
}

