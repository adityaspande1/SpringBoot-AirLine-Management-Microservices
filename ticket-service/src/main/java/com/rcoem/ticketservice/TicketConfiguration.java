package com.rcoem.ticketservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TicketConfiguration {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
