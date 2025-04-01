package com.example.movie.Service;

import com.example.movie.Model.Ticket;
import com.example.movie.Repository.TicketRepository;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }
}

