package com.example.movie.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.movie.Model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}