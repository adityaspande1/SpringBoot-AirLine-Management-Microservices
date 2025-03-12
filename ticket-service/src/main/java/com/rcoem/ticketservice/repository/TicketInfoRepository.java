package com.rcoem.ticketservice.repository;

import com.rcoem.ticketservice.dto.TicketInfo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class TicketInfoRepository {
    private final Map<String, TicketInfo> ticketInfoMap= new HashMap<>();

    public TicketInfo save(TicketInfo ticket) {
        ticketInfoMap.put(ticket.getTicketId(),ticket);
        return ticket;
    }

    public Optional<TicketInfo> findByTicketId(String ticketId) {
        return Optional.ofNullable(ticketInfoMap.get(ticketId));
    }

    public void deleteByTicketId(String ticketId) {
        ticketInfoMap.remove(ticketId);
    }

    public boolean existsByFlightAndEmail(Long flightNumber, String email) {
        return ticketInfoMap.values().stream()
                .anyMatch(t -> t.getFlightNumber().equals(flightNumber) && t.getEmail().equalsIgnoreCase(email));
    }
}
