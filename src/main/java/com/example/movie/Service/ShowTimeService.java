package com.example.movie.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movie.Model.Movie;
import com.example.movie.Model.Room;
import com.example.movie.Model.ShowTime;
import com.example.movie.Repository.RoomRepository;
import com.example.movie.Repository.ShowTimeRepository;

@Service
public class ShowTimeService {
    @Autowired
    private ShowTimeRepository showTimeRepository;
    
    @Autowired
    private RoomRepository roomRepository;
    
    public ShowTime createShowTime(ShowTime showTime) {
        return showTimeRepository.save(showTime);
    }
    
    public ShowTime updateShowTime(ShowTime showTime) {
        return showTimeRepository.save(showTime);
    }
    
    public void deleteShowTime(Long id) {
        showTimeRepository.deleteById(id);
    }
    
    public ShowTime getShowTimeById(Long id) {
        return showTimeRepository.findById(id).orElse(null);
    }
    
    public List<ShowTime> getAllShowTimes() {
        return showTimeRepository.findAll();
    }
    
    public List<ShowTime> getShowTimesByMovie(Movie movie) {
        return showTimeRepository.findByMovie(movie);
    }
    
    public List<ShowTime> getUpcomingShowTimesByMovie(Movie movie) {
        return showTimeRepository.findByMovieAndStartTimeGreaterThanOrderByStartTimeAsc(
            movie, LocalDateTime.now());
    }
    
    public List<ShowTime> getShowTimesBetween(LocalDateTime start, LocalDateTime end) {
        return showTimeRepository.findByStartTimeBetweenOrderByStartTimeAsc(start, end);
    }
    
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }
    
    public Room updateRoom(Room room) {
        return roomRepository.save(room);
    }
    
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
    
    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }
    
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Autowired
    private MovieService movieService;

    public List<ShowTime> getShowTimesByMovieId(Long movieId) {
        Movie movie = movieService.getMovieById(movieId);
        if (movie == null) {
            return List.of();
        }
        return showTimeRepository.findByMovieAndStartTimeGreaterThanOrderByStartTimeAsc(
            movie, LocalDateTime.now());
    }
}