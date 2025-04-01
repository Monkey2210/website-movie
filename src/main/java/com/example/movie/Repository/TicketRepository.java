package com.example.movie.Repository;

import com.example.movie.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByMovieId(Long movieId);

    // Tìm vé theo userId (Ví dụ: tìm vé của người dùng)
    List<Ticket> findByUserId(String userId);

    // Tìm vé theo showtime (Ví dụ: tìm vé của một thời gian cụ thể)
    List<Ticket> findByShowtime(String showtime);

    // Tìm vé theo movieId và userId (Ví dụ: kiểm tra vé của người dùng cho một phim cụ thể)
    Optional<Ticket> findByMovieIdAndUserId(Long movieId, String userId);

    // Tìm vé theo price (Ví dụ: tìm các vé có giá cụ thể)
    List<Ticket> findByPrice(Double price);
}
