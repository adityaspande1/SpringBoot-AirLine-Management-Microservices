package com.rcoem.ticketservice.service;

import com.rcoem.ticketservice.FlightServiceClient;
import com.rcoem.ticketservice.dto.TicketInfo;
import com.rcoem.ticketservice.dto.TicketRequest;
import com.rcoem.ticketservice.repository.TicketInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    TicketInfoRepository ticketInfoRepository;

    @Autowired
    private FlightServiceClient flightServiceClient;


    public TicketInfo bookTicket(TicketRequest ticketRequest) {
        // Check flight existence
        try {
            flightServiceClient.getFlightInfo(ticketRequest.getFlightNumber());
            System.out.println(ticketRequest.getFlightNumber());
        } catch (Exception e) {
            throw new RuntimeException("Invalid flight number: " + ticketRequest.getFlightNumber());
        }

        if(ticketInfoRepository.existsByFlightAndEmail(ticketRequest.getFlightNumber(), ticketRequest.getEmail())) {
            throw new RuntimeException("Ticket already booked for this flight with this email");
        }

        TicketInfo ticketInfo = new TicketInfo(
                UUID.randomUUID().toString(),
                ticketRequest.getFlightNumber(),
                ticketRequest.getPassengerName(),
                ticketRequest.getEmail()
        );

        return ticketInfoRepository.save(ticketInfo);
    }
    public TicketInfo getTicket(String id) {
        return ticketInfoRepository.findByTicketId(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }
    public void cancelTicket(String id) {
        Optional<TicketInfo> ticket = ticketInfoRepository.findByTicketId(id);
        if (ticket.isEmpty()) {
            throw new RuntimeException("Ticket not found");
        }
       ticketInfoRepository.deleteByTicketId(id);
    }


}
