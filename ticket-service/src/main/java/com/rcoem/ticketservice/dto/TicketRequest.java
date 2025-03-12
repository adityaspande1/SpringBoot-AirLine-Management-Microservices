package com.rcoem.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TicketRequest {
    private Long flightNumber;
    private String passengerName;
    private String email;
}
