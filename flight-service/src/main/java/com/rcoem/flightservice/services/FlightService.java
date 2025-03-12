package com.rcoem.flightservice.services;

import com.rcoem.flightservice.dto.FlightInfo;
import com.rcoem.flightservice.dto.FlightScheduleInfo;
import com.rcoem.flightservice.repository.FlightInfoRepository;
import com.rcoem.flightservice.repository.FlightScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class FlightService {

    @Autowired
    FlightInfoRepository flightInfoRepository;

    @Autowired
    FlightScheduleRepository flightScheduleRepository;


    public List<FlightInfo> getAllFlights(String sortValue) {
        List<FlightInfo> flights = flightInfoRepository.getAll();


        if (sortValue.equalsIgnoreCase("desc")) {
            flights.sort(Comparator.comparing(FlightInfo::getFlightNumber).reversed());
        } else {
            flights.sort(Comparator.comparing(FlightInfo::getFlightNumber));
        }


        return flights;
    }

    public ResponseEntity<FlightInfo> getFlightByFlightNumber(Long flightNumber) {
        List<FlightInfo> flights=flightInfoRepository.getAll();
        for(FlightInfo flight:flights){
            if(flight.getFlightNumber().equals(flightNumber)){
                return ResponseEntity.ok(flight);
            }
        }
        return ResponseEntity.notFound().build();
    }

    public List<FlightScheduleInfo> getAllFlightSchedules() {
        return flightScheduleRepository.getFlightScheduleInfoList();
    }

    public FlightScheduleInfo getFlightScheduleByFlightNumber(Long flightNumber) {
        List<FlightScheduleInfo> flights= flightScheduleRepository.getFlightScheduleInfoList();
        for(FlightScheduleInfo flight:flights){
            if(flight.getFlightNumber().equals(flightNumber)){
                return flight;
            }
        }
        return null;
    }

}
