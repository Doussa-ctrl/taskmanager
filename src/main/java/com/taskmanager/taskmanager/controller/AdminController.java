
package com.taskmanager.taskmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.taskmanager.taskmanager.model.Admin;
import com.taskmanager.taskmanager.service.AdminService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
@GetMapping("/admin/profile")

   
   public String profile(HttpSession session, Model model) {

    Admin admin = (Admin) session.getAttribute("loggedAdmin");

    if (admin== null) {
        return "redirect:/";
    }

    model.addAttribute("admin", admin);

    return "admin_profile";
} 
@GetMapping("/admin/dashboard")
public String dashboard (HttpSession session, Model model){
        Admin admin = (Admin) session.getAttribute("loggedAdmin");

    if (admin == null) {
        return "redirect:/";
    }
        model.addAttribute("totalUsers",
            adminService.getTotalUsers());

        model.addAttribute("activeUsers",
            adminService.getActiveUsers());

        model.addAttribute("inactiveUsers",
            adminService.getInactiveUsers());

        return "admin-dashboard";
    }

@GetMapping("/admin/users")
public String users( Model model,HttpSession session) {

     Admin admin = (Admin) session.getAttribute("loggedAdmin");

    if (admin == null) {
        return "redirect:/";
    }

    model.addAttribute("users", adminService.getAllUsers());

    return "users";
}

@PostMapping("/admin/users/{id}/disable")
public String disableUser(
        @PathVariable Long id,
        HttpSession session) {

     Admin admin = (Admin) session.getAttribute("loggedAdmin");

    if (admin == null) {
        return "redirect:/";
    }
    adminService.disableUser(id);

    return "redirect:/admin/users";
}

@PostMapping("/admin/users/{id}/activate")
public String activateUser(
        @PathVariable Long id,
        HttpSession session) {

     Admin admin = (Admin) session.getAttribute("loggedAdmin");

    if (admin == null) {
        return "redirect:/";
    }

    adminService.activateUser(id);

    return "redirect:/admin/users";
}
@PostMapping("/admin/users/{id}/delete")
public String deleteUser(
        @PathVariable Long id,
        HttpSession session
       ) {

    // التحقق من تسجيل دخول Admin
    Admin admin = (Admin) session.getAttribute("loggedAdmin");
    if (admin == null) {
        return "redirect:/";
    }
        adminService.deleteUser(id);
    return "redirect:/admin/users";
}
}
