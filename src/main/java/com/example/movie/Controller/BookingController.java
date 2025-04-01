package com.example.movie.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BookingController {

    @GetMapping("/booking-confirmation")
    public String showConfirmation() {
        return "booking-confirmation";
    }

    @PostMapping("/process-booking")  // Đổi từ /book thành /process-booking
    @ResponseBody
    public String processBooking() {
        // Xử lý logic đặt vé ở đây
        return "success";
    }
}