package com.rcoem.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TicketInfo {
    private String ticketId;
    private Long flightNumber;
    private String passengerName;
    private String email;
}