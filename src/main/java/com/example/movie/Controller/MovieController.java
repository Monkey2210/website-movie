package com.example.movie.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.movie.Model.Movie;

@Controller
public class MovieController {

    @GetMapping("/")
    public String index(Model model) {
        List<Movie> movies = new ArrayList<>(); // This will be replaced with actual API calls
        model.addAttribute("movies", movies);
        return "index";
    }
}

