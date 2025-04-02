package com.example.movie.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.movie.Model.Movie;
import com.example.movie.Service.MovieService;

@Controller
public class BookingController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/booking/{movieId}")
    public String showBookingPage(@PathVariable Long movieId, Model model) {
        Movie movie = movieService.getMovieById(movieId);
        if (movie == null) {
            return "redirect:/";
        }
        model.addAttribute("movie", movie);
        return "booking";
    }
}