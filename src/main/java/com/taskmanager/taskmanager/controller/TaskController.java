
package com.taskmanager.taskmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.taskmanager.taskmanager.enums.Priority;
import com.taskmanager.taskmanager.enums.TaskStatus;
import com.taskmanager.taskmanager.model.Task;
import com.taskmanager.taskmanager.model.User;
import com.taskmanager.taskmanager.service.TaskService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/tasks")
@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/new")
    public String showTaskForm(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute("task", new Task());

        return "add-task";
    }

    @PostMapping
    public String createTask(
            @ModelAttribute("task") Task task,
            HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/";
        }

        taskService.createTask(task, user);

        return "redirect:/tasks";
    }
     @GetMapping
    public String showTasks(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute(
                "tasks",
                taskService.getTasks(user.getId())
        );

        return "my-tasks";
    }
    @PostMapping("/status/{id}")
public String changeStatus(
        @PathVariable Long id,
        @RequestParam TaskStatus status,
        HttpSession session) {

    User user = (User) session.getAttribute("loggedUser");

    if (user == null) {
        return "redirect:/";
    }

    taskService.changeTaskStatus(id, user.getId(), status);

    return "redirect:/tasks";
}
@PostMapping("/delete/{id}")
public String deleteTask(
        @PathVariable Long id,
        HttpSession session) {

    User user = (User) session.getAttribute("loggedUser");

    if (user == null) {
        return "redirect:/";
    }

    taskService.deleteTask(id, user.getId());

    return "redirect:/tasks";
}
@GetMapping("/edit/{id}")
public String showEditForm(
        @PathVariable Long id,
        HttpSession session,
        Model model) {

    User user = (User) session.getAttribute("loggedUser");

    if (user == null) {
        return "redirect:/";
    }

    Task task = taskService.getTaskById(id, user.getId());

    model.addAttribute("task", task);

    return "edit-task";
}
@PostMapping("/edit/{id}")
public String updateTask(
        @PathVariable Long id,
        @ModelAttribute("task") Task updatedTask,
        HttpSession session) {

    User user = (User) session.getAttribute("loggedUser");

    if (user == null) {
        return "redirect:/";
    }

    taskService.updateTask(id, user.getId(), updatedTask);

    return "redirect:/tasks";
}
@GetMapping("/filter")
public String filterTasks(
        @RequestParam Priority priority,
        HttpSession session,
        Model model) {

    User user = (User) session.getAttribute("loggedUser");

    if (user == null) {
        return "redirect:/";
    }

    model.addAttribute(
            "tasks",
            taskService.getTasksByPriority(user.getId(), priority)
    );

    model.addAttribute("selectedPriority", priority);

    return "my-tasks";
}
}
