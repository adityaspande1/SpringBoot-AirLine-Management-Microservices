package com.rcoem.ticketservice.controller;

import com.rcoem.ticketservice.dto.TicketInfo;
import com.rcoem.ticketservice.dto.TicketRequest;
import com.rcoem.ticketservice.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping()
    public ResponseEntity<TicketInfo> createTicket(@RequestBody TicketRequest request) {
        TicketInfo response = ticketService.bookTicket(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketInfo> getTicket(@PathVariable String id) {
        TicketInfo ticket = ticketService.getTicket(id);
        return ResponseEntity.ok(ticket);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable String id) {
        ticketService.cancelTicket(id);
        return ResponseEntity.noContent().build();
    }


}
