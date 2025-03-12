package com.rcoem.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketResponse {
    private String id;
    private Long flightNumber;
    private String passengerName;
    private String email;


}