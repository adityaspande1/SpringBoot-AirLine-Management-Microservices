package com.rcoem.flightservice.repository;

import com.rcoem.flightservice.dto.FlightScheduleInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FlightScheduleRepository {

    private Map<String, FlightScheduleInfo> flightScheduleInfoMap;

    @PostConstruct
    public void init(){
     flightScheduleInfoMap= new HashMap<>();

        FlightScheduleInfo sch1= FlightScheduleInfo.builder()
                .scheduleId("sch-1")
                .flightNumber(101L)
                .departureTime(LocalDateTime.now().plusDays(1))
                .arrivalTime(LocalDateTime.now().plusDays(1).plusHours(2))
                .availableSeats(120)
                .build();

        FlightScheduleInfo sch2= FlightScheduleInfo.builder()
                .scheduleId("sch-2")
                .flightNumber(202L)
                .departureTime(LocalDateTime.now().plusDays(2))
                .arrivalTime(LocalDateTime.now().plusDays(2).plusHours(2))
                .availableSeats(140)
                .build();

        FlightScheduleInfo sch3= FlightScheduleInfo.builder()
                .scheduleId("sch-3")
                .flightNumber(303L)
                .departureTime(LocalDateTime.now().plusDays(3))
                .arrivalTime(LocalDateTime.now().plusDays(3).plusHours(2))
                .availableSeats(130)
                .build();


        flightScheduleInfoMap.put(sch1.getScheduleId(),sch1);
        flightScheduleInfoMap.put(sch2.getScheduleId(),sch2);
        flightScheduleInfoMap.put(sch3.getScheduleId(),sch3);

    }

    public List<FlightScheduleInfo> getFlightScheduleInfoList(){
        return new ArrayList<>(flightScheduleInfoMap.values());
    }


}
