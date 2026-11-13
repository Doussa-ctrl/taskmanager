
package com.taskmanager.taskmanager.service;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.taskmanager.taskmanager.enums.AccountStatus;
import com.taskmanager.taskmanager.model.Admin;
import com.taskmanager.taskmanager.model.User;
import com.taskmanager.taskmanager.repository.AdminRepository;
import com.taskmanager.taskmanager.repository.UserRepository;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    public AdminService(AdminRepository adminRepository,UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.userRepository=userRepository;
        ;
    }

    public Optional<Admin> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }
    public long countUsers() {
   
    return userRepository.count();
}

// عرض جميع المستخدمين
public List<User> getAllUsers() {
    return userRepository.findAll();
}

// تعطيل حساب
public void disableUser(Long id) {
    User user = userRepository.findById(id)
            .orElseThrow(() ->
                new RuntimeException("User not found"));

    user.setAccountStatus(AccountStatus.INACTIVE);
    userRepository.save(user);
}

// تفعيل حساب
public void activateUser(Long id) {
    User user = userRepository.findById(id)
            .orElseThrow(() ->
                new RuntimeException("User not found"));

    user.setAccountStatus(AccountStatus.ACTIVE);
    userRepository.save(user);
}



   public void deleteUser(Long id) {

    User user = userRepository.findById(id)
        .orElseThrow(() ->
            new RuntimeException("User not found"));

    userRepository.delete(user);
}

   

    public long getTotalUsers() {
        return userRepository.count();
    }

    public long getActiveUsers() {
        return userRepository.countByAccountStatus(
            AccountStatus.ACTIVE
        );
    }

    public long getInactiveUsers() {
        return userRepository.countByAccountStatus(
            AccountStatus.INACTIVE
        );
    }
}


