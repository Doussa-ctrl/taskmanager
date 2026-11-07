package com.taskmanager.taskmanager.service;



import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.taskmanager.taskmanager.enums.Priority;
import com.taskmanager.taskmanager.enums.TaskStatus;
import com.taskmanager.taskmanager.model.Task;
import com.taskmanager.taskmanager.model.User;
import com.taskmanager.taskmanager.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public long countTotalTasks(Long userId) {
        return taskRepository.countByUser_Id(userId);
    }

    public long countCompletedTasks(Long userId) {
        return taskRepository.countByUser_IdAndStatus(
            userId, TaskStatus.DONE
        );
    }

    public long countInProgressTasks(Long userId) {
        return taskRepository.countByUser_IdAndStatus(
            userId, TaskStatus.IN_PROGRESS
        );
    }

    public long countTodoTasks(Long userId) {
        return taskRepository.countByUser_IdAndStatus(
            userId, TaskStatus.TODO
        );
    }

    public long countOverdueTasks(Long userId) {
        return taskRepository.countByUser_IdAndDueDateBeforeAndStatusNot(
            userId, LocalDate.now(), TaskStatus.DONE
        );
    }
    
public Task createTask(Task task, User user) {

    task.setUser(user);

    task.setStatus(TaskStatus.TODO);

    return taskRepository.save(task);
}
 public List<Task> getActiveTasks(Long userId) {
    return taskRepository.findActiveTasks(userId, TaskStatus.DONE);
}
public void changeTaskStatus(Long taskId, Long userId, TaskStatus status) {

    Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found"));

    if (!task.getUser().getId().equals(userId)) {
        throw new RuntimeException("Unauthorized access");
    }

    task.setStatus(status);

    taskRepository.save(task);
}
public void deleteTask(Long taskId, Long userId) {

    Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found"));

    if (!task.getUser().getId().equals(userId)) {
        throw new RuntimeException("Unauthorized access");
    }

    taskRepository.delete(task);
}
public Task updateTask(Long taskId, Long userId, Task updatedTask) {

    Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found"));

    if (!task.getUser().getId().equals(userId)) {
        throw new RuntimeException("Unauthorized access");
    }

    task.setTitle(updatedTask.getTitle());
    task.setDescription(updatedTask.getDescription());
    task.setPriority(updatedTask.getPriority());
    task.setDueDate(updatedTask.getDueDate());

    return taskRepository.save(task);
}
public Task getTaskById(Long taskId, Long userId) {

    Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found"));

    if (!task.getUser().getId().equals(userId)) {
        throw new RuntimeException("Unauthorized access");
    }

    return task;
}
public List<Task> getTasksByPriority(Long userId, Priority priority) {

    return taskRepository
            .findByUser_IdAndStatusNotAndPriorityOrderByDueDateAsc(
                    userId,
                    TaskStatus.DONE,
                    priority
            );
}
}

