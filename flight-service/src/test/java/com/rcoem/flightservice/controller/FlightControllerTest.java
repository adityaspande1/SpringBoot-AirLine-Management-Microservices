package com.rcoem.flightservice.controller;

import com.rcoem.flightservice.dto.FlightInfo;
import com.rcoem.flightservice.dto.FlightScheduleInfo;
import com.rcoem.flightservice.services.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class FlightControllerTest {

    @Mock
    private FlightService flightService;

    @InjectMocks
    private FlightController flightController;

    private List<FlightInfo> mockFlights;
    private List<FlightScheduleInfo> mockSchedules;
    private FlightInfo mockFlight;
    private FlightScheduleInfo mockSchedule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup mock flight data
        mockFlight = FlightInfo.builder()
                .flightNumber(1L)
                .flightName("Air123")
                .flightType("Commercial")
                .airline("AirIndia")
                .build();

        mockFlights = Arrays.asList(
                mockFlight,
                FlightInfo.builder().flightNumber(2L).flightName("Air456").flightType("Commercial").airline("IndiGo").build()
        );

        // Setup mock schedule data
        LocalDateTime now = LocalDateTime.now();
        mockSchedule = FlightScheduleInfo.builder()
                .scheduleId("SCH001")
                .flightNumber(1L)
                .departureTime(now)
                .arrivalTime(now.plusHours(2))
                .availableSeats(100)
                .build();

        mockSchedules = Arrays.asList(
                mockSchedule,
                FlightScheduleInfo.builder()
                        .scheduleId("SCH002")
                        .flightNumber(2L)
                        .departureTime(now.plusHours(3))
                        .arrivalTime(now.plusHours(5))
                        .availableSeats(150)
                        .build()
        );
    }

    @Test
    void getAllFlights_WithDefaultSorting_ShouldReturnFlights() {
        // Arrange
        when(flightService.getAllFlights("asc")).thenReturn(mockFlights);

        // Act
        List<FlightInfo> result = flightController.getAllFlights("asc");

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getFlightNumber());
        assertEquals("Air123", result.get(0).getFlightName());
        verify(flightService, times(1)).getAllFlights("asc");
    }

    @Test
    void getFlightById_ExistingFlight_ShouldReturnFlight() {
        // Arrange
        when(flightService.getFlightByFlightNumber(1L))
                .thenReturn(ResponseEntity.ok(mockFlight));

        // Act
        ResponseEntity<FlightInfo> result = flightController.getFlightById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1L, result.getBody().getFlightNumber());
        assertEquals("Air123", result.getBody().getFlightName());
        verify(flightService, times(1)).getFlightByFlightNumber(1L);
    }

    @Test
    void getFlightSchedules_ShouldReturnAllSchedules() {
        // Arrange
        when(flightService.getAllFlightSchedules()).thenReturn(mockSchedules);

        // Act
        List<FlightScheduleInfo> result = flightController.getFlightSchedules();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("SCH001", result.get(0).getScheduleId());
        assertEquals(1L, result.get(0).getFlightNumber());
        verify(flightService, times(1)).getAllFlightSchedules();
    }

    @Test
    void getFlightSchedule_ExistingFlight_ShouldReturnSchedule() {
        // Arrange
        when(flightService.getFlightScheduleByFlightNumber(1L)).thenReturn(mockSchedule);

        // Act
        FlightScheduleInfo result = flightController.getFlightSchedule(1L);

        // Assert
        assertNotNull(result);
        assertEquals("SCH001", result.getScheduleId());
        assertEquals(1L, result.getFlightNumber());
        assertEquals(100, result.getAvailableSeats());
        verify(flightService, times(1)).getFlightScheduleByFlightNumber(1L);
    }
} 