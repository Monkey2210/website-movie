package com.example.movie.Repository;

import com.example.movie.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser_Email(String email);  // Sửa từ findByCustomerEmail thành findByUser_Email
    List<Booking> findByMovieId(Long movieId);
}