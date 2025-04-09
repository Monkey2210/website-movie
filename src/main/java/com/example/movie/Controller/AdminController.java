package com.example.movie.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.movie.Service.UserService;
import com.example.movie.Service.MovieService;
import com.example.movie.Service.TicketService;
import com.example.movie.Model.User;
import com.example.movie.Model.Movie;
import com.example.movie.Model.Ticket;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("userCount", userService.getUserCount());
        model.addAttribute("movieCount", movieService.getMovieCount());
        model.addAttribute("ticketCount", ticketService.getTicketCount());
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/users/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/user-details";
    }

    @GetMapping("/movies")
    public String listMovies(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "admin/movies";
    }

    @GetMapping("/movies/{id}")
    public String viewMovie(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        return "admin/movie-details";
    }

    @GetMapping("/tickets")
    public String listTickets(Model model) {
        model.addAttribute("tickets", ticketService.getAllTickets());
        return "admin/tickets";
    }

    @GetMapping("/tickets/{id}")
    public String viewTicket(@PathVariable Long id, Model model) {
        model.addAttribute("ticket", ticketService.getTicketById(id));
        return "admin/ticket-details";
    }

    @PostMapping("/users/add")
    public String addUser(User user) {
        try {
            userService.registerUser(user);
            return "redirect:/admin/users";
        } catch (Exception e) {
            return "redirect:/admin/users?error=" + e.getMessage();
        }
    }

    @PostMapping("/users/edit")
    public String editUser(User user) {
        try {
            User existingUser = userService.getUserById(user.getId());
            if (existingUser != null) {
                existingUser.setUsername(user.getUsername());
                existingUser.setEmail(user.getEmail());
                existingUser.setRole(user.getRole());
                existingUser.setActive(user.isActive());
                userService.updateUser(existingUser);
            }
            return "redirect:/admin/users";
        } catch (Exception e) {
            return "redirect:/admin/users?error=" + e.getMessage();
        }
    }

    @PostMapping("/users/delete")
    public String deleteUser(Long id) {
        try {
            userService.deleteUser(id);
            return "redirect:/admin/users";
        } catch (Exception e) {
            return "redirect:/admin/users?error=" + e.getMessage();
        }
    }
}