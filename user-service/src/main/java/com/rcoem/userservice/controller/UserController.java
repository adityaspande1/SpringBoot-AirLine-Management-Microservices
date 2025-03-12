package com.rcoem.userservice.controller;

import com.rcoem.userservice.dto.FlightInfo;
import com.rcoem.userservice.dto.TicketInfo;
import com.rcoem.userservice.dto.TicketRequest;
import com.rcoem.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/flights")
    public List<FlightInfo> getAllFlights() {
        return userService.getAllFlights();
    }

    @PostMapping("/book")
    public TicketInfo bookTicket(@RequestBody TicketRequest ticketRequest) {
        return userService.bookTicket(ticketRequest);
    }

    @GetMapping("/ticket/{id}")
    public TicketInfo getTicket(@PathVariable String id) {
        return userService.getTicket(id);
    }

    @DeleteMapping("/ticket/{id}")
    public ResponseEntity<String> cancelTicket(@PathVariable String id) {
        userService.cancelTicket(id);
        return ResponseEntity.ok("Ticket cancelled successfully");
    }


}
