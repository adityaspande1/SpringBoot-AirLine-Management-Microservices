package com.rcoem.userservice.service;

import com.rcoem.userservice.dto.FlightInfo;
import com.rcoem.userservice.dto.TicketInfo;
import com.rcoem.userservice.dto.TicketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;

    private final String FLIGHT_SERVICE_URL = "http://localhost:8080";
    private final String TICKET_SERVICE_URL="http://localhost:8081/ticket-management/tickets";

    @Override
    public List<FlightInfo> getAllFlights() {
        ResponseEntity<FlightInfo[]> response = restTemplate.getForEntity(
                FLIGHT_SERVICE_URL + "/flights", FlightInfo[].class);
        return Arrays.asList(response.getBody());
    }
    @Override
    public TicketInfo bookTicket(TicketRequest request) {
        return restTemplate.postForObject(TICKET_SERVICE_URL, request, TicketInfo.class);

    }

    @Override
    public TicketInfo getTicket(String ticketId) {
        return restTemplate.getForObject(TICKET_SERVICE_URL + "/" + ticketId, TicketInfo.class);
    }

    @Override
    public void cancelTicket(String ticketId) {
        restTemplate.delete(TICKET_SERVICE_URL + "/" + ticketId);
    }
}
