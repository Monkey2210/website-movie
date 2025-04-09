package com.example.movie.Controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.movie.Model.Movie;
import com.example.movie.Model.Room;
import com.example.movie.Model.ShowTime;
import com.example.movie.Service.MovieService;
import com.example.movie.Service.ShowTimeService;

@Controller
@RequestMapping("/admin")
public class AdminShowTimeController {
    
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AdminShowTimeController.class);

    @Autowired
    private ShowTimeService showTimeService;
    
    @Autowired
    private MovieService movieService;

    @PostMapping("/movies/{id}/showtimes/add")
    public String addShowTimeForMovie(@PathVariable Long id,
                                    @RequestParam String showDate,
                                    @RequestParam String showTime,
                                    @RequestParam Long roomId,
                                    @RequestParam double price) {
        try {
            // Validate input parameters
            if (showDate == null || showDate.trim().isEmpty() || 
                showTime == null || showTime.trim().isEmpty()) {
                logger.error("Invalid date or time input: date={}, time={}", showDate, showTime);
                return "redirect:/admin/movies?error=invalid_datetime";
            }

            Movie movie = movieService.getMovieById(id);
            Room room = showTimeService.getRoomById(roomId);
            
            if (movie == null) {
                logger.error("Movie not found with id: {}", id);
                return "redirect:/admin/movies?error=movie_not_found";
            }
            
            if (room == null) {
                logger.error("Room not found with id: {}", roomId);
                return "redirect:/admin/movies?error=room_not_found";
            }

            if (price <= 0) {
                logger.error("Invalid price value: {}", price);
                return "redirect:/admin/movies?error=invalid_price";
            }
            
            try {
                // Format date and time properly before parsing
                String dateTimeStr = showDate.trim() + "T" + showTime.trim();
                LocalDateTime startTime = LocalDateTime.parse(dateTimeStr);
                
                if (startTime.isBefore(LocalDateTime.now())) {
                    logger.error("Show time is in the past: {}", startTime);
                    return "redirect:/admin/movies?error=past_datetime";
                }

                ShowTime showTimeEntity = new ShowTime(startTime, price, movie, room);
                showTimeService.createShowTime(showTimeEntity);
                logger.info("Successfully created show time for movie id: {} at {}", id, startTime);
                return "redirect:/admin/movies?success=true";
            } catch (java.time.format.DateTimeParseException e) {
                logger.error("Failed to parse date time: {} {}", showDate, showTime, e);
                return "redirect:/admin/movies?error=invalid_datetime_format";
            }
        } catch (Exception e) {
            logger.error("Unexpected error while adding show time for movie id: {}", id, e);
            return "redirect:/admin/movies?error=unexpected";
        }
    }
    
    @GetMapping("/showtimes")
    public String showShowTimes(Model model) {
        model.addAttribute("showTimes", showTimeService.getAllShowTimes());
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("rooms", showTimeService.getAllRooms());
        return "admin/showtimes";
    }
    
    @GetMapping("/rooms")
    public String showRooms(Model model) {
        model.addAttribute("rooms", showTimeService.getAllRooms());
        return "admin/rooms";
    }
    
    @PostMapping("/showtimes/add")
    public String addShowTime(@RequestParam Long movieId,
                            @RequestParam Long roomId,
                            @RequestParam LocalDateTime startTime,
                            @RequestParam double price) {
        Movie movie = movieService.getMovieById(movieId);
        Room room = showTimeService.getRoomById(roomId);
        
        if (movie != null && room != null) {
            ShowTime showTime = new ShowTime(startTime, price, movie, room);
            showTimeService.createShowTime(showTime);
        }
        
        return "redirect:/admin/showtimes";
    }
    
    @PostMapping("/rooms/add")
    public String addRoom(@RequestParam String name,
                         @RequestParam int capacity) {
        Room room = new Room(name, capacity);
        showTimeService.createRoom(room);
        return "redirect:/admin/rooms";
    }
    
    @DeleteMapping("/showtimes/delete/{id}")
    @ResponseBody
    public void deleteShowTime(@PathVariable Long id) {
        showTimeService.deleteShowTime(id);
    }
    
    @DeleteMapping("/rooms/delete/{id}")
    @ResponseBody
    public void deleteRoom(@PathVariable Long id) {
        showTimeService.deleteRoom(id);
    }
    
    @GetMapping("/showtimes/edit/{id}")
    public String editShowTime(@PathVariable Long id, Model model) {
        ShowTime showTime = showTimeService.getShowTimeById(id);
        if (showTime != null) {
            model.addAttribute("showTime", showTime);
            model.addAttribute("movies", movieService.getAllMovies());
            model.addAttribute("rooms", showTimeService.getAllRooms());
            return "admin/editShowTime";
        }
        return "redirect:/admin/showtimes";
    }
    
    @GetMapping("/rooms/edit/{id}")
    public String editRoom(@PathVariable Long id, Model model) {
        Room room = showTimeService.getRoomById(id);
        if (room != null) {
            model.addAttribute("room", room);
            return "admin/editRoom";
        }
        return "redirect:/admin/rooms";
    }
    
    @PostMapping("/showtimes/edit/{id}")
    public String updateShowTime(@PathVariable Long id,
                               @RequestParam Long movieId,
                               @RequestParam Long roomId,
                               @RequestParam LocalDateTime startTime,
                               @RequestParam double price) {
        ShowTime showTime = showTimeService.getShowTimeById(id);
        if (showTime != null) {
            Movie movie = movieService.getMovieById(movieId);
            Room room = showTimeService.getRoomById(roomId);
            
            if (movie != null && room != null) {
                showTime.setMovie(movie);
                showTime.setRoom(room);
                showTime.setStartTime(startTime);
                showTime.setPrice(price);
                showTimeService.updateShowTime(showTime);
            }
        }
        return "redirect:/admin/showtimes";
    }
    
    @PostMapping("/rooms/edit/{id}")
    public String updateRoom(@PathVariable Long id,
                           @RequestParam String name,
                           @RequestParam int capacity) {
        Room room = showTimeService.getRoomById(id);
        if (room != null) {
            room.setName(name);
            room.setCapacity(capacity);
            showTimeService.updateRoom(room);
        }
        return "redirect:/admin/rooms";
    }
}