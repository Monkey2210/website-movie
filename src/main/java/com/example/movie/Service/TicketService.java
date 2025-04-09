package com.example.movie.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.movie.Model.Ticket;
import com.example.movie.Repository.TicketRepository;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public long getTicketCount() {
        return ticketRepository.count();
    }
}

