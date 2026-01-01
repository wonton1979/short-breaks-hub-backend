package com.shortbreakshub.seeder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortbreakshub.model.DayPlan;
import com.shortbreakshub.model.Itinerary;
import com.shortbreakshub.model.ItineraryPlanningSnapshot;
import com.shortbreakshub.repository.ItineraryPlanningSnapshotRepository;
import com.shortbreakshub.seeder.dto.SeedDayDTO;
import com.shortbreakshub.seeder.dto.SeedItineraryDTO;
import com.shortbreakshub.seeder.dto.SeedPlanningDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import com.shortbreakshub.repository.ItineraryRepository;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ItinerarySeeder implements CommandLineRunner {

    private final ItineraryRepository itineraryRepository;
    private final ObjectMapper objectMapper;
    private final ItineraryPlanningSnapshotRepository snapshotRepository;

    public ItinerarySeeder(ItineraryRepository itineraryRepository, ObjectMapper objectMapper, ItineraryPlanningSnapshotRepository snapshotRepository) {
        this.itineraryRepository = itineraryRepository;
        this.objectMapper = objectMapper;
        this.snapshotRepository = snapshotRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        ClassPathResource resource = new ClassPathResource("seed/itineraries.json");
        try (InputStream in =  resource.getInputStream() ) {
            List<SeedItineraryDTO> seedItineraries =
                    objectMapper.readValue(in, new TypeReference<List<SeedItineraryDTO>>() {});


            if (itineraryRepository.count() == 0) {
                List<Itinerary> itineraries = seedItineraries.stream()
                        .map(this::toItineraryEntity)
                        .toList();
                itineraryRepository.saveAll(itineraries);
            } else {
                System.out.println("Itinerary Already Exists (" + itineraryRepository.count() + ")");
            }

            Map<String, Itinerary> itineraryBySlug = itineraryRepository.findAll().stream()
                    .collect(Collectors.toMap(Itinerary::getSlug, Function.identity(),(a,b)->a));

            int created = 0;
            for (SeedItineraryDTO dto : seedItineraries) {
                if (dto.getPlanning() == null) continue;

                Itinerary itinerary = itineraryBySlug.get(dto.getSlug());
                if (itinerary == null) continue;

                if (snapshotRepository.existsByItinerary_Id(itinerary.getId())) continue;

                ItineraryPlanningSnapshot snap = toSnapshotEntity(dto.getPlanning(), itinerary);
                snapshotRepository.save(snap);
                created++;
            }
            System.out.println("Seeded snapshots = " + created);
        }
    }

    private Itinerary toItineraryEntity(SeedItineraryDTO dto) {
        Itinerary itinerary = new Itinerary();

        itinerary.setSlug(dto.getSlug());
        itinerary.setRegion(dto.getRegion());
        itinerary.setCountry(dto.getCountry());
        itinerary.setCity(dto.getCity());
        itinerary.setTitle(dto.getTitle());
        itinerary.setDays(dto.getDays());
        itinerary.setPriceFrom(dto.getPriceFrom());
        itinerary.setHero(dto.getHero());
        itinerary.setSummary(dto.getSummary());
        itinerary.setHighlights(dto.getHighlights());
        itinerary.setDayPlans(
                dto.getSchedule().stream()
                        .map(this::toDayPlanEmbeddable)
                        .toList()
        );

        return itinerary;
    }

    private DayPlan toDayPlanEmbeddable(SeedDayDTO dayDto) {
        DayPlan dayPlan = new DayPlan();
        dayPlan.setDay(dayDto.getDay());
        dayPlan.setTitle(dayDto.getTitle());
        dayPlan.setSummary(dayDto.getSummary());
        dayPlan.setDetails(dayDto.getDetails());
        return dayPlan;
    }

    private ItineraryPlanningSnapshot toSnapshotEntity(SeedPlanningDTO planning, Itinerary itinerary) {
        ItineraryPlanningSnapshot snap = new ItineraryPlanningSnapshot();
        snap.setItinerary(itinerary);

        snap.setCity(planning.getCity());

        snap.setBestTimeMonths(planning.getBestTime().getMonths());
        snap.setBestTimeNote(planning.getBestTime().getNote());

        snap.setWorstTimeMonths(planning.getWorstTime().getMonths());
        snap.setWorstTimeNote(planning.getWorstTime().getNote());

        snap.setTips(planning.getTips());
        snap.setWithKids(planning.getWithKids());

        return snap;
    }


}
