package com.rcoem.flightservice.repository;

import com.rcoem.flightservice.dto.FlightInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FlightInfoRepository {
    Map<Number, FlightInfo> flightInfoTable;

    @PostConstruct
    public void init(){
        flightInfoTable = new HashMap<>();

        FlightInfo flight1 = FlightInfo.builder()
                .flightNumber(101L)
                .flightName("SpeedJet 101")
                .flightType("Domestic")
                .airline("SpeedJet")
                .build();

        FlightInfo flight2 = FlightInfo.builder()
                .flightNumber(202L)
                .flightName("AirIndia 202")
                .flightType("International")
                .airline("Air India")
                .build();

        FlightInfo flight3 = FlightInfo.builder()
                .flightNumber(303L)
                .flightName("Indigo 303")
                .flightType("Domestic")
                .airline("Indigo")
                .build();

        flightInfoTable.put(flight1.getFlightNumber(), flight1);
        flightInfoTable.put(flight2.getFlightNumber(), flight2);
        flightInfoTable.put(flight3.getFlightNumber(), flight3);

    }


    public List<FlightInfo> getAll() {
        return new ArrayList<>(flightInfoTable.values());
    }

    public List<Number> getAllFlightNumbers(){
        return new ArrayList<>(flightInfoTable.keySet());
    }
    public Optional<FlightInfo> getById(Number flightNumber) {
            return Optional.ofNullable(flightInfoTable.get(flightNumber));
    }
}
