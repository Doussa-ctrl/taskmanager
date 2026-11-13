
package com.taskmanager.taskmanager.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taskmanager.taskmanager.enums.NotificationType;
import com.taskmanager.taskmanager.enums.TaskStatus;
import com.taskmanager.taskmanager.model.Task;
import com.taskmanager.taskmanager.repository.TaskRepository;
import com.taskmanager.taskmanager.repository.NotificationRepository;

@Service
public class TaskReminderService {

    private final TaskRepository taskRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    public TaskReminderService(
            TaskRepository taskRepository,
            NotificationRepository notificationRepository,
            NotificationService notificationService) {

        this.taskRepository = taskRepository;
        this.notificationRepository = notificationRepository;
        this.notificationService = notificationService;
    }

    // Check task deadlines every day at 8 AM
  @Scheduled(
    cron = "0 0 0 * * *",
    zone = "Africa/Algiers"
)
    @Transactional
    public void checkTaskDeadlines() {

        LocalDate today =
            LocalDate.now(ZoneId.of("Africa/Algiers"));

        LocalDate tomorrow = today.plusDays(1);

        // Active tasks only
        List<TaskStatus> activeStatuses = List.of(
            TaskStatus.TODO,
            TaskStatus.IN_PROGRESS
        );

        // Find tasks due today, tomorrow or overdue
        List<Task> tasks =
            taskRepository.findByStatusInAndDueDateLessThanEqual(
                activeStatuses,
                tomorrow
            );

        for (Task task : tasks) {

            NotificationType type;

            if (task.getDueDate().isEqual(tomorrow)) {

                type = NotificationType.TASK_DUE_TOMORROW;

            } else if (task.getDueDate().isEqual(today)) {

                type = NotificationType.TASK_DUE_TODAY;

            } else {

                type = NotificationType.TASK_OVERDUE;
            }

            // Prevent duplicate notifications
            boolean alreadyExists =
    
    notificationRepository.existsByUserAndTaskAndType(
        task.getUser(),
        task,
        type
    );
    

            if (!alreadyExists) {

                String title;
                String message;

                switch (type) {

                    case TASK_DUE_TOMORROW:
                        title = "Task Due Tomorrow";
                        message = "Your task is due tomorrow: "
                                + task.getTitle();
                        break;

                    case TASK_DUE_TODAY:
                        title = "Task Due Today";
                        message = "Your task is due today: "
                                + task.getTitle();
                        break;

                    default:
                        title = "Overdue Task";
                        message = "Your task is overdue: "
                                + task.getTitle();
                        break;
                }

                // Save notification
                notificationService.createNotification(
                    title,
                    message,
                    type,
                    task.getUser(),
                    task
                );
            }
        }
    }
}

