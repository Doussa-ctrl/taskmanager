
package com.taskmanager.taskmanager.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
   @Query("""
    SELECT t FROM Task t
    WHERE t.user.id = :userId
    AND t.status <> :status
    ORDER BY
        CASE
            WHEN t.priority = com.taskmanager.taskmanager.enums.Priority.HIGH THEN 1
            WHEN t.priority = com.taskmanager.taskmanager.enums.Priority.MEDIUM THEN 2
            WHEN t.priority = com.taskmanager.taskmanager.enums.Priority.LOW THEN 3
        END ASC,
        t.dueDate Asc
""")
List<Task> findActiveTasks(
        @Param("userId") Long userId,
        @Param("status") TaskStatus status
);
List<Task> findByUser_IdAndStatusNotAndPriorityOrderByDueDateAsc(
        Long userId,
        TaskStatus status,
        Priority priority
);
}


