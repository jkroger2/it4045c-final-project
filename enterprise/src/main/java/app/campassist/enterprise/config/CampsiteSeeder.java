package app.campassist.enterprise.config;

import app.campassist.enterprise.model.Campsite;
import app.campassist.enterprise.repository.CampsiteRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Profile("dev")
public class CampsiteSeeder implements CommandLineRunner {

    private final CampsiteRepository campsiteRepository;

    public CampsiteSeeder(CampsiteRepository campsiteRepository) {
        this.campsiteRepository = campsiteRepository;
    }
    
    @Override
    public void run(String... args) {
        campsiteRepository.deleteAll();

        campsiteRepository.saveAll(List.of(
            new Campsite(
                null, 
                "Sunny Campground", 
                "A beautiful campsite near the lake.",
                "123 Lakeview Rd",
                "Lakeside", 
                "CA", 
                "90210",
                BigDecimal.valueOf(35.5), 
                BigDecimal.valueOf(45.0),
                List.of(
                    "WiFi",
                    "Fire Pit",
                    "Picnic Tables"
                ),
                List.of(
                    "https://images.pexels.com/photos/1309587/pexels-photo-1309587.jpeg"
                ),
                List.of(
                    "family-friendly", 
                    "pet-friendly"
                )
            ),
            new Campsite(
                null,
                "Forest Retreat",
                "Secluded spot surrounded by pine trees.",
                "45 Pine St",
                "Evergreen",
                "CO",
                "80439",
                BigDecimal.valueOf(28.0),
                BigDecimal.valueOf(50.0),
                List.of(
                    "Hiking Trails",
                    "Fishing"
                ),
                List.of(
                    "https://images.pexels.com/photos/2509093/pexels-photo-2509093.jpeg"
                ),
                List.of(
                    "quiet",
                    "nature"
                )
            ),
            new Campsite(
                null, 
                "Beachside Bliss", 
                "Steps from the sand and waves.",
                "78 Ocean Ave", 
                "Santa Monica", 
                "CA", 
                "90401",
                BigDecimal.valueOf(32.0), 
                BigDecimal.valueOf(60.0),
                List.of(
                    "Beach Access", 
                    "Bonfire"
                ),
                List.of(
                    "https://images.pexels.com/photos/2535207/pexels-photo-2535207.jpeg"
                ),
                List.of(
                    "scenic", 
                    "romantic"
                )
            ),
            new Campsite(
                null,
                "Mountain View Camp",
                "Panoramic views of the mountains.",
                "12 Alpine Rd", 
                "Boulder", 
                "CO", 
                "80302",
                BigDecimal.valueOf(40.0), 
                BigDecimal.valueOf(55.0),
                List.of(
                    "Hiking Trails", 
                    "Rock Climbing"
                ),
                List.of(
                    "https://images.pexels.com/photos/3014303/pexels-photo-3014303.jpeg"
                ),
                List.of(
                    "adventure", 
                    "photography"
                )
            ),
            new Campsite(
                null, 
                "Riverbend Camp", 
                "Camp along a peaceful river bend.",
                "99 River Rd", 
                "Bend", 
                "OR", 
                "97701",
                BigDecimal.valueOf(30.0), 
                BigDecimal.valueOf(40.0),
                List.of(
                    "Fishing", 
                    "Kayaking"
                ),
                List.of(
                    "https://images.pexels.com/photos/17816414/pexels-photo-17816414.jpeg"
                ),
                List.of(
                    "family-friendly"
                )
            ),
            new Campsite(
                null, 
                "Desert Oasis", 
                "Experience the desert night sky.",
                "250 Sand Dune Blvd", 
                "Phoenix", 
                "AZ", 
                "85001",
                BigDecimal.valueOf(25.0), 
                BigDecimal.valueOf(35.0),
                List.of(
                    "Star Gazing", 
                    "Campfire"
                ),
                List.of(
                    "https://images.pexels.com/photos/13504488/pexels-photo-13504488.jpeg"
                ),
                List.of(
                    "unique", 
                    "scenic"
                )
            ),
            new Campsite(
                null, 
                "Lakeside Haven", 
                "Quiet spot by a small lake.",
                "11 Lakeview Dr", 
                "Traverse City", 
                "MI", 
                "49684",
                BigDecimal.valueOf(28.0), 
                BigDecimal.valueOf(42.0),
                List.of(
                    "Boating", 
                    "Fishing"),
                List.of(
                    "https://images.pexels.com/photos/6271725/pexels-photo-6271725.jpeg"
                ),
                List.of(
                    "family-friendly", 
                    "relaxing"
                )
            ),
            new Campsite(
                null, 
                "Meadow Camp", 
                "Open meadows surrounded by hill.",
                "75 Meadow Ln", 
                "Madison", 
                "WI", 
                "53703",
                BigDecimal.valueOf(30.0), 
                BigDecimal.valueOf(38.0),
                List.of(
                    "Picnic Tables", 
                    "Hiking Trails"
                ),
                List.of(
                    "https://images.pexels.com/photos/2873086/pexels-photo-2873086.jpeg"
                ),
                List.of("quiet", "nature")
            )
        ));
        
        System.out.println(
            String.format(
                "[DEV PROFILE ACTIVE] Recreated database with %d records.", 
                campsiteRepository.count()
            )
        );
    }
}
