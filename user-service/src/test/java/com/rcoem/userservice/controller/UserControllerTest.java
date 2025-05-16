package com.rcoem.userservice.controller;

import com.rcoem.userservice.dto.FlightInfo;
import com.rcoem.userservice.dto.TicketInfo;
import com.rcoem.userservice.dto.TicketRequest;
import com.rcoem.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private List<FlightInfo> flightList;
    private TicketInfo ticketInfo;
    private TicketRequest ticketRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup flight data
        FlightInfo flight1 = new FlightInfo();
        flight1.setFlightNumber(1L);
        flight1.setFlightName("Air123");
        flight1.setAirline("AirIndia");

        FlightInfo flight2 = new FlightInfo();
        flight2.setFlightNumber(2L);
        flight2.setFlightName("Air456");
        flight2.setAirline("IndiGo");

        flightList = Arrays.asList(flight1, flight2);

        // Setup ticket data
        ticketInfo = new TicketInfo();
        ticketInfo.setTicketId("ticket-123");
        ticketInfo.setFlightNumber(1L);
        ticketInfo.setPassengerName("John Doe");
        ticketInfo.setEmail("john.doe@example.com");

        // Setup ticket request
        ticketRequest = new TicketRequest();
        ticketRequest.setFlightNumber(1L);
        ticketRequest.setPassengerName("John Doe");
        ticketRequest.setEmail("john.doe@example.com");
    }

    @Test
    void getAllFlights_ShouldReturnFlightList() {
        // Arrange
        when(userService.getAllFlights()).thenReturn(flightList);

        // Act
        List<FlightInfo> result = userController.getAllFlights();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getFlightNumber());
        assertEquals("Air123", result.get(0).getFlightName());
        assertEquals("AirIndia", result.get(0).getAirline());
        verify(userService, times(1)).getAllFlights();
    }

    @Test
    void bookTicket_ShouldReturnTicketInfo() {
        // Arrange
        when(userService.bookTicket(any(TicketRequest.class))).thenReturn(ticketInfo);

        // Act
        TicketInfo result = userController.bookTicket(ticketRequest);

        // Assert
        assertNotNull(result);
        assertEquals("ticket-123", result.getTicketId());
        assertEquals(1L, result.getFlightNumber());
        assertEquals("John Doe", result.getPassengerName());
        assertEquals("john.doe@example.com", result.getEmail());
        verify(userService, times(1)).bookTicket(any(TicketRequest.class));
    }

    @Test
    void getTicket_ShouldReturnTicketInfo() {
        // Arrange
        when(userService.getTicket("ticket-123")).thenReturn(ticketInfo);

        // Act
        TicketInfo result = userController.getTicket("ticket-123");

        // Assert
        assertNotNull(result);
        assertEquals("ticket-123", result.getTicketId());
        assertEquals(1L, result.getFlightNumber());
        assertEquals("John Doe", result.getPassengerName());
        verify(userService, times(1)).getTicket("ticket-123");
    }

    @Test
    void cancelTicket_ShouldReturnSuccessMessage() {
        // Arrange
        doNothing().when(userService).cancelTicket("ticket-123");

        // Act
        ResponseEntity<String> response = userController.cancelTicket("ticket-123");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ticket cancelled successfully", response.getBody());
        verify(userService, times(1)).cancelTicket("ticket-123");
    }
} 