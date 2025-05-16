package com.rcoem.flightservice.services;

import com.rcoem.flightservice.dto.FlightInfo;
import com.rcoem.flightservice.dto.FlightScheduleInfo;
import com.rcoem.flightservice.repository.FlightInfoRepository;
import com.rcoem.flightservice.repository.FlightScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightServiceTest {

    @Mock
    private FlightInfoRepository flightInfoRepository;

    @Mock
    private FlightScheduleRepository flightScheduleRepository;

    @InjectMocks
    private FlightService flightService;

    private List<FlightInfo> mockFlights;
    private List<FlightScheduleInfo> mockSchedules;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup mock flight data
        mockFlights = Arrays.asList(
                FlightInfo.builder().flightNumber(1L).flightName("Air123").flightType("Commercial").airline("AirIndia").build(),
                FlightInfo.builder().flightNumber(2L).flightName("Air456").flightType("Commercial").airline("IndiGo").build(),
                FlightInfo.builder().flightNumber(3L).flightName("Air789").flightType("Commercial").airline("SpiceJet").build()
        );

        // Setup mock schedule data
        LocalDateTime now = LocalDateTime.now();
        mockSchedules = Arrays.asList(
                FlightScheduleInfo.builder()
                        .scheduleId("SCH001")
                        .flightNumber(1L)
                        .departureTime(now)
                        .arrivalTime(now.plusHours(2))
                        .availableSeats(100)
                        .build(),
                FlightScheduleInfo.builder()
                        .scheduleId("SCH002")
                        .flightNumber(2L)
                        .departureTime(now.plusHours(3))
                        .arrivalTime(now.plusHours(5))
                        .availableSeats(150)
                        .build()
        );

        // Configure mocks
        when(flightInfoRepository.getAll()).thenReturn(mockFlights);
        when(flightScheduleRepository.getFlightScheduleInfoList()).thenReturn(mockSchedules);
    }

    @Test
    void getAllFlights_SortAscending_ShouldReturnSortedFlights() {
        // Arrange
        String sortValue = "asc";

        // Act
        List<FlightInfo> result = flightService.getAllFlights(sortValue);

        // Assert
        assertEquals(3, result.size());
        assertEquals(1L, result.get(0).getFlightNumber());
        assertEquals(2L, result.get(1).getFlightNumber());
        assertEquals(3L, result.get(2).getFlightNumber());
        verify(flightInfoRepository, times(1)).getAll();
    }

    @Test
    void getAllFlights_SortDescending_ShouldReturnReverseSortedFlights() {
        // Arrange
        String sortValue = "desc";

        // Act
        List<FlightInfo> result = flightService.getAllFlights(sortValue);

        // Assert
        assertEquals(3, result.size());
        assertEquals(3L, result.get(0).getFlightNumber());
        assertEquals(2L, result.get(1).getFlightNumber());
        assertEquals(1L, result.get(2).getFlightNumber());
        verify(flightInfoRepository, times(1)).getAll();
    }

    @Test
    void getFlightByFlightNumber_ExistingFlight_ShouldReturnFlight() {
        // Arrange
        Long flightNumber = 1L;

        // Act
        ResponseEntity<FlightInfo> result = flightService.getFlightByFlightNumber(flightNumber);

        // Assert
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(flightNumber, result.getBody().getFlightNumber());
        assertEquals("Air123", result.getBody().getFlightName());
        verify(flightInfoRepository, times(1)).getAll();
    }

    @Test
    void getFlightByFlightNumber_NonExistingFlight_ShouldReturnNotFound() {
        // Arrange
        Long flightNumber = 99L;

        // Act
        ResponseEntity<FlightInfo> result = flightService.getFlightByFlightNumber(flightNumber);

        // Assert
        assertTrue(result.getStatusCode().is4xxClientError());
        assertNull(result.getBody());
        verify(flightInfoRepository, times(1)).getAll();
    }

    @Test
    void getAllFlightSchedules_ShouldReturnAllSchedules() {
        // Act
        List<FlightScheduleInfo> result = flightService.getAllFlightSchedules();

        // Assert
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getFlightNumber());
        assertEquals(2L, result.get(1).getFlightNumber());
        verify(flightScheduleRepository, times(1)).getFlightScheduleInfoList();
    }

    @Test
    void getFlightScheduleByFlightNumber_ExistingFlight_ShouldReturnSchedule() {
        // Arrange
        Long flightNumber = 1L;

        // Act
        FlightScheduleInfo result = flightService.getFlightScheduleByFlightNumber(flightNumber);

        // Assert
        assertNotNull(result);
        assertEquals(flightNumber, result.getFlightNumber());
        assertEquals("SCH001", result.getScheduleId());
        assertNotNull(result.getDepartureTime());
        assertNotNull(result.getArrivalTime());
        assertEquals(100, result.getAvailableSeats());
        verify(flightScheduleRepository, times(1)).getFlightScheduleInfoList();
    }

    @Test
    void getFlightScheduleByFlightNumber_NonExistingFlight_ShouldReturnNull() {
        // Arrange
        Long flightNumber = 99L;

        // Act
        FlightScheduleInfo result = flightService.getFlightScheduleByFlightNumber(flightNumber);

        // Assert
        assertNull(result);
        verify(flightScheduleRepository, times(1)).getFlightScheduleInfoList();
    }
} 