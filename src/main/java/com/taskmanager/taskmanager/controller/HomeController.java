
package com.taskmanager.taskmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.taskmanager.taskmanager.model.Admin;
import com.taskmanager.taskmanager.model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
public String home(HttpSession session) {

    Admin admin = (Admin) session.getAttribute("loggedAdmin");

    if (admin != null) {
        return "redirect:/admin/dashboard";
    }

    User user = (User) session.getAttribute("loggedUser");

    if (user != null) {
        return "redirect:/user/dashboard";
    }

    return "home";
}

   

@GetMapping("/profile")
public String profile(HttpSession session, Model model) {

    User user = (User) session.getAttribute("loggedUser");

    if (user == null) {
        return "redirect:/";
    }

    model.addAttribute("user", user);

    return "profile";
}


}
