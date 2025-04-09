package com.example.movie.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.movie.Model.Movie;
import com.example.movie.Model.ShowTime;

public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {
    List<ShowTime> findByMovie(Movie movie);
    List<ShowTime> findByMovieAndStartTimeGreaterThanOrderByStartTimeAsc(Movie movie, LocalDateTime startTime);
    List<ShowTime> findByStartTimeBetweenOrderByStartTimeAsc(LocalDateTime startTime, LocalDateTime endTime);
}