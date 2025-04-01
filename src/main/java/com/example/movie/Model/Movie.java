package com.example.movie.Model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    private Long id;
    
    private String title;
    
    @Column(length = 1000)
    private String overview;
    
    private String posterPath;
    
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    
    private Double voteAverage;

    // Constructors
    public Movie() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }
}
