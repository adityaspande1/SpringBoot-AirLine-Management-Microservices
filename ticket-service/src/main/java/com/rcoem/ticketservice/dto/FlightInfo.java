package com.rcoem.ticketservice.dto;

import lombok.Data;

@Data
public class FlightInfo {
    private Long flightNumber;
    private String flightName;
    private String flightType;
    private String airline;
}