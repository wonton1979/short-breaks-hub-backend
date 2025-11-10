package com.shortbreakshub.seeder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortbreakshub.model.Itinerary;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import com.shortbreakshub.repository.ItineraryRepository;

import java.io.InputStream;
import java.util.List;

@Component
public class ItinerarySeeder implements CommandLineRunner {

    private final ItineraryRepository itineraryRepository;
    private final ObjectMapper objectMapper;
    public ItinerarySeeder(ItineraryRepository itineraryRepository, ObjectMapper objectMapper) {
        this.itineraryRepository = itineraryRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (itineraryRepository.count() == 0) {
            ClassPathResource resource = new ClassPathResource("seed/itineraries.json");
            try (InputStream in =  resource.getInputStream() ) {
                List<Itinerary> itineraries = objectMapper.readValue(in, new TypeReference<>() {
                });
                itineraryRepository.saveAll(itineraries);
            }
        }
        else  {
            System.out.println("Itinerary Already Exists");
        }

    }
}
