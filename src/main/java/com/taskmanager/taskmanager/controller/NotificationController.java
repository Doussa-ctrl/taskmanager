
package com.taskmanager.taskmanager.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.taskmanager.taskmanager.model.User;
import com.taskmanager.taskmanager.service.NotificationService;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
            NotificationService notificationService) {

        this.notificationService = notificationService;
    }

    // Display all notifications
    @GetMapping
    public String showNotifications(
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute(
            "notifications",
            notificationService.getUserNotifications(user)
        );

        model.addAttribute(
            "unreadCount",
            notificationService.countUnreadNotifications(user)
        );

        model.addAttribute("currentView", "all");

        return "notifications";
    }

    // Display unread notifications only
    @GetMapping("/unread")
    public String showUnreadNotifications(
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute(
            "notifications",
            notificationService.getUnreadNotifications(user)
        );

        model.addAttribute(
            "unreadCount",
            notificationService.countUnreadNotifications(user)
        );

        model.addAttribute("currentView", "unread");

        return "notifications";
    }

    // Mark notification as read
    @PostMapping("/{id}/read")
    public String markAsRead(
            @PathVariable Long id,
            HttpSession session) {

        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/";
        }

        notificationService.markAsRead(id, user);

        return "redirect:/notifications";
    }
    @PostMapping("/{id}/delete")
public String deleteNotification(
        @PathVariable Long id,
        HttpSession session) {

    User user = (User) session.getAttribute("loggedUser");

    if (user == null) {
        return "redirect:/";
    }

    notificationService.deleteNotification(id, user);

    return "redirect:/notifications";
}
}
