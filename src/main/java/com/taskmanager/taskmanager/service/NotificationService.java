
package com.taskmanager.taskmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taskmanager.taskmanager.enums.NotificationType;
import com.taskmanager.taskmanager.model.Notification;
import com.taskmanager.taskmanager.model.Task;
import com.taskmanager.taskmanager.model.User;
import com.taskmanager.taskmanager.repository.NotificationRepository;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(
            NotificationRepository notificationRepository) {

        this.notificationRepository = notificationRepository;
    }

    // Get all notifications
    public List<Notification> getUserNotifications(User user) {

        return notificationRepository
                .findByUserOrderByCreatedAtDesc(user);
    }

    // Get unread notifications
    public List<Notification> getUnreadNotifications(User user) {

        return notificationRepository
                .findByUserAndIsReadFalseOrderByCreatedAtDesc(user);
    }

    // Count unread notifications
    public long countUnreadNotifications(User user) {

        return notificationRepository
                .countByUserAndIsReadFalse(user);
    }

    // Mark notification as read
    @Transactional
    public void markAsRead(Long id, User user) {

        Notification notification = notificationRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                    new RuntimeException("Notification not found")
                );

        notification.setRead(true);

        notificationRepository.save(notification);
    }
    // Create and save notification
public Notification createNotification(
        String title,
        String message,
        NotificationType type,
        User user,
        Task task) {

    boolean exists = notificationRepository
            .existsByUserAndTaskAndType(user, task, type);

    if (exists) {
        return null;
    }

    Notification notification = new Notification();

    notification.setTitle(title);
    notification.setMessage(message);
    notification.setType(type);
    notification.setUser(user);
    notification.setTask(task);
    notification.setRead(false);

    return notificationRepository.save(notification);
}
public void deleteNotification(Long id, User user) {

    Notification notification = notificationRepository
            .findById(id)
            .orElseThrow(() ->
                new RuntimeException("Notification not found"));

    if (!notification.getUser().getId().equals(user.getId())) {
        throw new RuntimeException("Unauthorized");
    }

    notificationRepository.delete(notification);
}
}
