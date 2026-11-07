
package com.taskmanager.taskmanager.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.taskmanager.taskmanager.model.Task;
import com.taskmanager.taskmanager.enums.Priority;
import com.taskmanager.taskmanager.enums.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Total tasks
    long countByUser_Id(Long userId);

    // Completed tasks
    long countByUser_IdAndStatus(Long userId, TaskStatus status);

    // Overdue tasks
    long countByUser_IdAndDueDateBeforeAndStatusNot(
            Long userId,
            LocalDate date,
            TaskStatus status
    );
   
List<Task> findByUser_IdAndStatusNot(
        Long userId,
        TaskStatus status
);
List<Task> findByUser_IdAndStatusNotAndPriorityOrderByDueDateAsc(
        Long userId,
        TaskStatus status,
        Priority priority
);
}


