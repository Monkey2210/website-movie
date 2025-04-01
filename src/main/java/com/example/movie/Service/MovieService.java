package com.example.movie.Service;

import com.example.movie.Model.Movie;
import com.example.movie.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    
    @Autowired
    private MovieRepository movieRepository;

    // Lấy tất cả phim
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // Lưu phim mới hoặc cập nhật phim đã có
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    // Tìm phim theo ID
    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    // Xóa phim theo ID
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    // Tìm phim theo tên
    public List<Movie> findByTitleContaining(String title) {
        return movieRepository.findByTitleContaining(title);
    }

    // Kiểm tra phim đã tồn tại chưa
    public boolean existsById(Long id) {
        return movieRepository.existsById(id);
    }
}


