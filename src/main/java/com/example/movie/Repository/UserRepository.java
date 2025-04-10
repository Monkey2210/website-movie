package com.example.movie.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.movie.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}