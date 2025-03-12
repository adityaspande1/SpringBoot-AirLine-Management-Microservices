package com.rcoem.flightservice.controller;

import com.rcoem.flightservice.dto.FlightInfo;

import com.rcoem.flightservice.dto.FlightScheduleInfo;
import com.rcoem.flightservice.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    //get all flight details .
    @GetMapping
    public List<FlightInfo> getAllFlights(@RequestParam(name = "sort", defaultValue = "asc") String sortValue){
        return flightService.getAllFlights(sortValue);
    }
    //get flight details by id of flight
    @GetMapping("/{id}")
    public ResponseEntity<FlightInfo> getFlightById(@PathVariable Long id){
        System.out.println("inside getFlighht serivce");
        return flightService.getFlightByFlightNumber(id);
    }

    //get all flight schedules
    @GetMapping("/schedules")
    public List<FlightScheduleInfo> getFlightSchedules(){
        return flightService.getAllFlightSchedules();
    }

    //get flight schedules with flight id and date
    @GetMapping("/{id}/schedules")
    public FlightScheduleInfo getFlightSchedule(@PathVariable Long id){
        return flightService.getFlightScheduleByFlightNumber(id);
    }


}
