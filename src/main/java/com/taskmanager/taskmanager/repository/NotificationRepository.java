
package com.taskmanager.taskmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanager.taskmanager.enums.NotificationType;
import com.taskmanager.taskmanager.model.Notification;
import com.taskmanager.taskmanager.model.Task;
import com.taskmanager.taskmanager.model.User;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    // Get all notifications of a user
    List<Notification> findByUserOrderByCreatedAtDesc(User user);

    // Get unread notifications
    List<Notification> findByUserAndIsReadFalseOrderByCreatedAtDesc(
        User user
    );

    // Count unread notifications
    long countByUserAndIsReadFalse(User user);

    // Find a notification belonging to a specific user
    Optional<Notification> findByIdAndUser(Long id, User user);
  
void deleteByIdAndUser_Id(Long id, Long userId);

boolean existsByUserAndTaskAndType( User user,Task task , NotificationType type);
}
