package com.rcoem.ticketservice.controller;

import com.rcoem.ticketservice.dto.TicketInfo;
import com.rcoem.ticketservice.dto.TicketRequest;
import com.rcoem.ticketservice.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    private TicketInfo ticketInfo;
    private TicketRequest ticketRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup test data
        ticketInfo = TicketInfo.builder()
                .ticketId("ticket-123")
                .flightNumber(1L)
                .passengerName("John Doe")
                .email("john.doe@example.com")
                .build();

        ticketRequest = new TicketRequest();
        ticketRequest.setFlightNumber(1L);
        ticketRequest.setPassengerName("John Doe");
        ticketRequest.setEmail("john.doe@example.com");
    }

    @Test
    void createTicket_ValidRequest_ShouldReturnCreatedTicket() {
        // Arrange
        when(ticketService.bookTicket(any(TicketRequest.class))).thenReturn(ticketInfo);

        // Act
        ResponseEntity<TicketInfo> response = ticketController.createTicket(ticketRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ticket-123", response.getBody().getTicketId());
        assertEquals(1L, response.getBody().getFlightNumber());
        assertEquals("John Doe", response.getBody().getPassengerName());
        assertEquals("john.doe@example.com", response.getBody().getEmail());
        verify(ticketService, times(1)).bookTicket(any(TicketRequest.class));
    }

    @Test
    void getTicket_ExistingTicket_ShouldReturnTicket() {
        // Arrange
        when(ticketService.getTicket("ticket-123")).thenReturn(ticketInfo);

        // Act
        ResponseEntity<TicketInfo> response = ticketController.getTicket("ticket-123");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ticket-123", response.getBody().getTicketId());
        assertEquals(1L, response.getBody().getFlightNumber());
        assertEquals("John Doe", response.getBody().getPassengerName());
        verify(ticketService, times(1)).getTicket("ticket-123");
    }

    @Test
    void deleteTicket_ExistingTicket_ShouldReturnNoContent() {
        // Arrange
        doNothing().when(ticketService).cancelTicket("ticket-123");

        // Act
        ResponseEntity<Void> response = ticketController.deleteTicket("ticket-123");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(ticketService, times(1)).cancelTicket("ticket-123");
    }
} 