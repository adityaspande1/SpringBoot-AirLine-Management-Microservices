package com.rcoem.userservice.service;

import com.rcoem.userservice.dto.FlightInfo;
import com.rcoem.userservice.dto.TicketInfo;
import com.rcoem.userservice.dto.TicketRequest;

import java.util.List;

public interface UserService {
    public List<FlightInfo> getAllFlights();
    public TicketInfo bookTicket(TicketRequest request);
    public TicketInfo getTicket(String ticketId);
    public void cancelTicket(String ticketId);

}
