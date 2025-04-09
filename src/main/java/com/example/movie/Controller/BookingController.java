package com.example.movie.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.movie.Model.Movie;
import com.example.movie.Model.ShowTime;
import com.example.movie.Service.MovieService;
import com.example.movie.Service.ShowTimeService;

import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ShowTimeService showTimeService;

    @GetMapping("/booking/{movieId}")
    public String showBookingPage(@PathVariable Long movieId, Model model) {
        Movie movie = movieService.getMovieById(movieId);
        if (movie == null) {
            return "redirect:/";
        }
        
        List<ShowTime> showTimes = showTimeService.getShowTimesByMovieId(movieId);
        
        model.addAttribute("movie", movie);
        model.addAttribute("showTimes", showTimes);
        return "booking";
    }
}