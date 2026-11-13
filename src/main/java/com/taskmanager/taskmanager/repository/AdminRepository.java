
package com.taskmanager.taskmanager.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.taskmanager.taskmanager.model.Admin;

public interface AdminRepository
        extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);
}
