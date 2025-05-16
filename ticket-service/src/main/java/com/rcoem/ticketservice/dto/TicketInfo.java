package com.rcoem.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketInfo {
    private String ticketId;
    private Long flightNumber;
    private String passengerName;
    private String email;
}
