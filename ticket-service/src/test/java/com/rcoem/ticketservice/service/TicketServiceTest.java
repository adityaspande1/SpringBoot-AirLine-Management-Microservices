package com.rcoem.ticketservice.service;

import com.rcoem.ticketservice.FlightServiceClient;
import com.rcoem.ticketservice.dto.FlightInfo;
import com.rcoem.ticketservice.dto.TicketInfo;
import com.rcoem.ticketservice.dto.TicketRequest;
import com.rcoem.ticketservice.repository.TicketInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TicketServiceTest {

    @Mock
    private TicketInfoRepository ticketInfoRepository;

    @Mock
    private FlightServiceClient flightServiceClient;

    @InjectMocks
    private TicketServiceImpl ticketService;

    private TicketRequest ticketRequest;
    private TicketInfo ticketInfo;
    private FlightInfo flightInfo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup test data
        ticketRequest = new TicketRequest();
        ticketRequest.setFlightNumber(1L);
        ticketRequest.setPassengerName("John Doe");
        ticketRequest.setEmail("john.doe@example.com");

        ticketInfo = TicketInfo.builder()
                .ticketId("ticket-123")
                .flightNumber(1L)
                .passengerName("John Doe")
                .email("john.doe@example.com")
                .build();

        flightInfo = new FlightInfo();
        flightInfo.setFlightNumber(1L);
        flightInfo.setFlightName("Air123");
    }

    @Test
    void bookTicket_ValidRequest_ShouldCreateTicket() {
        // Arrange
        when(flightServiceClient.getFlightInfo(eq(1L))).thenReturn(flightInfo);
        when(ticketInfoRepository.existsByFlightAndEmail(eq(1L), eq("john.doe@example.com"))).thenReturn(false);
        when(ticketInfoRepository.save(any(TicketInfo.class))).thenReturn(ticketInfo);

        // Act
        TicketInfo result = ticketService.bookTicket(ticketRequest);

        // Assert
        assertNotNull(result);
        assertEquals("ticket-123", result.getTicketId());
        assertEquals(1L, result.getFlightNumber());
        assertEquals("John Doe", result.getPassengerName());
        assertEquals("john.doe@example.com", result.getEmail());

        verify(flightServiceClient, times(1)).getFlightInfo(eq(1L));
        verify(ticketInfoRepository, times(1)).existsByFlightAndEmail(eq(1L), eq("john.doe@example.com"));
        verify(ticketInfoRepository, times(1)).save(any(TicketInfo.class));
    }

    @Test
    void bookTicket_InvalidFlight_ShouldThrowException() {
        // Arrange
        when(flightServiceClient.getFlightInfo(eq(1L))).thenThrow(new RuntimeException("Flight not found"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ticketService.bookTicket(ticketRequest);
        });

        assertTrue(exception.getMessage().contains("Invalid flight number"));
        verify(flightServiceClient, times(1)).getFlightInfo(eq(1L));
        verify(ticketInfoRepository, never()).save(any(TicketInfo.class));
    }

    @Test
    void bookTicket_DuplicateBooking_ShouldThrowException() {
        // Arrange
        when(flightServiceClient.getFlightInfo(eq(1L))).thenReturn(flightInfo);
        when(ticketInfoRepository.existsByFlightAndEmail(eq(1L), eq("john.doe@example.com"))).thenReturn(true);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ticketService.bookTicket(ticketRequest);
        });

        assertTrue(exception.getMessage().contains("Ticket already booked"));
        verify(flightServiceClient, times(1)).getFlightInfo(eq(1L));
        verify(ticketInfoRepository, times(1)).existsByFlightAndEmail(eq(1L), eq("john.doe@example.com"));
        verify(ticketInfoRepository, never()).save(any(TicketInfo.class));
    }

    @Test
    void getTicket_ExistingTicket_ShouldReturnTicket() {
        // Arrange
        when(ticketInfoRepository.findByTicketId(eq("ticket-123"))).thenReturn(Optional.of(ticketInfo));

        // Act
        TicketInfo result = ticketService.getTicket("ticket-123");

        // Assert
        assertNotNull(result);
        assertEquals("ticket-123", result.getTicketId());
        assertEquals("John Doe", result.getPassengerName());
        verify(ticketInfoRepository, times(1)).findByTicketId(eq("ticket-123"));
    }

    @Test
    void getTicket_NonExistingTicket_ShouldThrowException() {
        // Arrange
        when(ticketInfoRepository.findByTicketId(eq("invalid-id"))).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ticketService.getTicket("invalid-id");
        });

        assertTrue(exception.getMessage().contains("Ticket not found"));
        verify(ticketInfoRepository, times(1)).findByTicketId(eq("invalid-id"));
    }

    @Test
    void cancelTicket_ExistingTicket_ShouldDeleteTicket() {
        // Arrange
        when(ticketInfoRepository.findByTicketId(eq("ticket-123"))).thenReturn(Optional.of(ticketInfo));
        doNothing().when(ticketInfoRepository).deleteByTicketId(eq("ticket-123"));

        // Act
        ticketService.cancelTicket("ticket-123");

        // Assert
        verify(ticketInfoRepository, times(1)).findByTicketId(eq("ticket-123"));
        verify(ticketInfoRepository, times(1)).deleteByTicketId(eq("ticket-123"));
    }

    @Test
    void cancelTicket_NonExistingTicket_ShouldThrowException() {
        // Arrange
        when(ticketInfoRepository.findByTicketId(eq("invalid-id"))).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ticketService.cancelTicket("invalid-id");
        });

        assertTrue(exception.getMessage().contains("Ticket not found"));
        verify(ticketInfoRepository, times(1)).findByTicketId(eq("invalid-id"));
        verify(ticketInfoRepository, never()).deleteByTicketId(any());
    }
} 