package com.example.movie.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.movie.Model.Ticket;
import com.example.movie.Service.TicketService;

@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/booking")
    public String showBookingForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "booking";
    }

    @PostMapping("/book")
    public String bookTicket(Ticket ticket) {
        ticketService.saveTicket(ticket);
        return "redirect:/index"; // Sau khi đặt vé thành công, quay lại trang chủ
    }
}

