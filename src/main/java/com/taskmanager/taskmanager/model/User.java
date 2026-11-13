package com.taskmanager.taskmanager.model;

    

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.taskmanager.taskmanager.enums.AccountStatus;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(unique = true, nullable = false)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String picture;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    private String provider;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public User() {
    }

  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.REMOVE,
    orphanRemoval = true
)
private List<Task> tasks = new ArrayList<>();

public List<Task> getTasks() {
    return tasks;
}

public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
}
@Enumerated(EnumType.STRING)
@Column(nullable = false)
private AccountStatus accountStatus;

public AccountStatus getAccountStatus() {
    return accountStatus;
}

public void setAccountStatus(AccountStatus accountStatus) {
    this.accountStatus = accountStatus;
}
@Column(name = "created_at")
private LocalDateTime createdAt;
public LocalDateTime getCreatedAt() {
    return createdAt;
}

public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
}
@PrePersist
public void onCreate() {
    if (createdAt == null) {
        createdAt = LocalDateTime.now();
    }
}
}