package com.example.movie.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private int capacity;
    
    @OneToMany(mappedBy = "room")
    private List<ShowTime> showTimes;
    
    public Room() {}
    
    public Room(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public List<ShowTime> getShowTimes() {
        return showTimes;
    }
    
    public void setShowTimes(List<ShowTime> showTimes) {
        this.showTimes = showTimes;
    }
}