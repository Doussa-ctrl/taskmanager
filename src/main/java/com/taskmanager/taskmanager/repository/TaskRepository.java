
package com.taskmanager.taskmanager.repository;

import java.time.LocalDate;
import java.util.Collection;
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
   List<Task> findByUser_Id(Long userId);

List<Task> findByUser_IdAndPriorityOrderByDueDateAsc(
        Long userId,
        Priority priority
);
  List<Task> findByStatusInAndDueDateLessThanEqual(
        Collection<TaskStatus> statuses,
        LocalDate dueDate
    );
}


