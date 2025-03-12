package com.rcoem.ticketservice;

import com.rcoem.ticketservice.dto.FlightInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FlightServiceClient {

    @Autowired
    private RestTemplate restTemplate;
    public FlightInfo getFlightInfo(Long flightNumber) {
        String url = "http://localhost:8080/flights/" + flightNumber; // Change port if needed
        return restTemplate.getForObject(url, FlightInfo.class);
    }


}
