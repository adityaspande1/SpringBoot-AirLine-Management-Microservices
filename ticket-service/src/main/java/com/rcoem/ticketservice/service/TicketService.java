package com.rcoem.ticketservice.service;

import com.rcoem.ticketservice.dto.TicketInfo;
import com.rcoem.ticketservice.dto.TicketRequest;

public interface TicketService {
    TicketInfo bookTicket(TicketRequest request);
    TicketInfo getTicket(String id);
    void cancelTicket(String id);
}
