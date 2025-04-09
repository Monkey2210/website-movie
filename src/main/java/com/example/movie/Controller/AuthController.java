package com.example.movie.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.movie.Model.User;  // Add this import
import com.example.movie.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "auth";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, 
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam(required = false) boolean isAdmin,
                             Model model) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);  // UserService will handle password encoding
            user.setRole(isAdmin ? "ADMIN" : "USER");
            
            userService.registerUser(user);
            model.addAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "auth";
        } catch (Exception e) {
            model.addAttribute("error", "Đăng ký thất bại: " + e.getMessage());
            return "auth";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                       @RequestParam String password,
                       HttpSession session,
                       Model model) {
        if (userService.validateUser(username, password)) {
            User user = userService.findByUsername(username);
            session.setAttribute("loggedInUser", user);
            
            // Chuyển hướng dựa trên vai trò
            if ("ADMIN".equals(user.getRole())) {
                return "redirect:/admin/dashboard";
            }
            return "redirect:/";
        } else {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
            return "auth";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loggedInUser");
        return "redirect:/";
    }
}