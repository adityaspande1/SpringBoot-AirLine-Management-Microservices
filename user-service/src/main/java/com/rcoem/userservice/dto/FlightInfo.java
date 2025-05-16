package com.rcoem.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightInfo {
    private Long flightNumber;
    private String flightName;
    private String flightType;
    private String airline;

}