package com.taskmanager.taskmanager.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.taskmanager.taskmanager.model.User;
import com.taskmanager.taskmanager.service.TaskService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

    private final TaskService taskService;

    public DashboardController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/user/dashboard")
    public String dashboard(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/";
        }

        Long userId = user.getId();

        model.addAttribute("totalTasks",
                taskService.countTotalTasks(userId));

        model.addAttribute("completedTasks",
                taskService.countCompletedTasks(userId));

        model.addAttribute("inProgressTasks",
                taskService.countInProgressTasks(userId));

        model.addAttribute("todoTasks",
                taskService.countTodoTasks(userId));

        model.addAttribute("overdueTasks",
                taskService.countOverdueTasks(userId));

        return "dashboard_user";
    }
}
