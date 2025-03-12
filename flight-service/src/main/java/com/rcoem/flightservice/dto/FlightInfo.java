package com.rcoem.flightservice.dto;

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