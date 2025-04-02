package com.example.movie.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.movie.Model.Movie;
import com.example.movie.Repository.MovieRepository;

import jakarta.annotation.PostConstruct;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    
    @Value("${tmdb.api.key}")
    private String apiKey;

    private final String BASE_URL = "https://api.themoviedb.org/3";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Movie> getAllMovies() {
        try {
            String url = BASE_URL + "/movie/popular?api_key=" + apiKey + "&language=vi-VN&page=1";
            System.out.println("Calling TMDB API with URL: " + url);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            ResponseEntity<String> response = restTemplate.exchange(
                url, 
                HttpMethod.GET, 
                entity, 
                String.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JSONObject jsonObject = new JSONObject(response.getBody());
                JSONArray results = jsonObject.getJSONArray("results");
                System.out.println("Found " + results.length() + " movies in API response");
                
                List<Movie> movies = new ArrayList<>();
                for (int i = 0; i < results.length(); i++) {
                    try {
                        JSONObject movieJson = results.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setId(movieJson.getLong("id"));
                        
                        // Check if movie already exists
                        Optional<Movie> existingMovie = movieRepository.findById(movie.getId());
                        if (existingMovie.isPresent()) {
                            movies.add(existingMovie.get());
                            continue;
                        }
                        
                        movie.setTitle(movieJson.getString("title"));
                        movie.setPosterPath(movieJson.getString("poster_path"));
                        movie.setVoteAverage(movieJson.getDouble("vote_average"));
                        
                        if (!movieJson.isNull("release_date")) {
                            String releaseDateStr = movieJson.getString("release_date");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            movie.setReleaseDate(dateFormat.parse(releaseDateStr));
                        }
                        
                        movieRepository.save(movie);
                        movies.add(movie);
                        System.out.println("Successfully processed movie: " + movie.getTitle());
                    } catch (Exception e) {
                        System.err.println("Error processing movie: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
                
                if (!movies.isEmpty()) {
                    return movies;
                }
            }
            
            // If no movies from API, try database
            List<Movie> dbMovies = movieRepository.findAll();
            if (!dbMovies.isEmpty()) {
                return dbMovies;
            }
            
        } catch (Exception e) {
            System.err.println("Error in MovieService: " + e.getMessage());
            e.printStackTrace();
        }
        
        return new ArrayList<>();
    }

    @PostConstruct
    public void testDatabaseConnection() {
        try {
            System.out.println("Đang kiểm tra kết nối database...");
            System.out.println("URL: " + movieRepository.toString());
            List<Movie> test = movieRepository.findAll();
            System.out.println("Kết nối database thành công!");
            System.out.println("Số lượng phim trong database: " + test.size());
        } catch (Exception e) {
            System.err.println("Kết nối database thất bại!");
            System.err.println("Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public Movie getMovieById(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.orElse(null);
    }
}


