
package com.taskmanager.taskmanager.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.taskmanager.taskmanager.enums.TaskStatus;
import com.taskmanager.taskmanager.enums.Priority;
@Entity
@Table(name = "tasks")
public class Task {
public Task() {
}
public Task(String title, String description,
            TaskStatus status, Priority priority,
            LocalDate dueDate) {

    this.title = title;
    this.description = description;
    this.status = status;
    this.priority = priority;
    this.dueDate = dueDate;
}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
private String title;
    private String description;
@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueDate;
   
@Enumerated(EnumType.STRING)
@Column(nullable = false)
private TaskStatus status;

@Enumerated(EnumType.STRING)
@Column(nullable = false)
private Priority priority;

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getTitle() {
    return title;
}

public void setTitle(String title) {
    this.title = title;
}

public String getDescription() {
    return description;
}

public void setDescription(String description) {
    this.description = description;
}

public TaskStatus getStatus() {
    return status;
}

public void setStatus(TaskStatus status) {
    this.status = status;
}

public Priority getPriority() {
    return priority;
}

public void setPriority(Priority priority) {
    this.priority = priority;
}

public LocalDate getDueDate() {
    return dueDate;
}

public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
}
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
private User user;

public User getUser() {
    return user;
}

public void setUser(User user) {
    this.user = user;
}
@OneToMany(
    mappedBy = "task",
    cascade = CascadeType.REMOVE,
    orphanRemoval = true
)
private List<Notification> notifications = new ArrayList<>();

public List<Notification> getNotifications() {
    return notifications;
}

public void setNotifications(List<Notification> notifications) {
    this.notifications = notifications;
}
}
