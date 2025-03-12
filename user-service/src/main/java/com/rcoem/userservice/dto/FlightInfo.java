package com.rcoem.userservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightInfo {
    private Long flightNumber;
    private String flightName;
    private String flightType;
    private String airline;

}