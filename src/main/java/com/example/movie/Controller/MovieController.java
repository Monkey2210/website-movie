package com.example.movie.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.movie.Model.Movie;
import com.example.movie.Service.MovieService;

@Controller
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/")
    public String showMovies(Model model) {
        try {
            List<Movie> movies = movieService.getAllMovies();
            if (movies == null || movies.isEmpty()) {
                model.addAttribute("error", "No movies available at the moment");
                return "error";
            }
            model.addAttribute("movies", movies);
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error loading movies: " + e.getMessage());
            return "error";
        }
    }
}

