package app.campassist.enterprise.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import app.campassist.enterprise.dto.CampsiteDTO;

@Service
public class CampsiteServiceStub implements ICampsiteService {

    private final List<CampsiteDTO> campsites = new ArrayList<>();

    public CampsiteServiceStub() {
        // In memory stub data
        campsites.add(
            new CampsiteDTO(
                UUID.randomUUID(), 
                "Mountain View Campground",
                "A beautiful campsite with stunning mountain views.", 
                "123 Forest Lane",
                "Springfield",
                "IL",
                "62701",
                25.0,
                50.00,
                List.of("Hiking", "Fishing", "Campfire"),
                List.of("hhttps://images.pexels.com/photos/1309587/pexels-photo-1309587.jpeg"),
                List.of("scenic", "family-friendly")
            )
        );
        campsites.add(
            new CampsiteDTO(
                UUID.randomUUID(), 
                "Lakeside Retreat",
                "Enjoy fishing and boating at this serene lakeside campsite.", 
                "456 Lakeview Drive",
                "Lakeside",
                "CA",
                "92040",
                30.5,
                75.00,
                List.of("Fishing", "Boating", "Hiking"),
                List.of("https://images.pexels.com/photos/4993949/pexels-photo-4993949.jpeg"),
                List.of("waterfront", "nature")
            )
        );
    }
    
    @Override
    public List<CampsiteDTO> fetchAllCampsites() {
        return campsites;
    }

    @Override
    public CampsiteDTO fetchCampsiteById(String id) {

        return campsites.stream()
            .filter(campsite -> campsite.getId().toString().equals(id))
            .findFirst()
            .orElse(null);
    }

    @Override
    public CampsiteDTO addCampsite(CampsiteDTO dto) {
        CampsiteDTO campsite = new CampsiteDTO(
            UUID.randomUUID(),
            dto.getName(),
            dto.getDescription(),
            dto.getAddress(),
            dto.getCity(),
            dto.getState(),
            dto.getZipCode(),
            dto.getMaxRigLength(),
            dto.getPricePerNight(),
            dto.getAmenities(),
            dto.getImages(),
            dto.getTags()
        );
        campsites.add(campsite);
        return campsite;
    }

    @Override
    public CampsiteDTO updateCampsite(CampsiteDTO campsite) {
        CampsiteDTO existingCampsite = fetchCampsiteById(campsite.getId().toString());
        campsites.remove(existingCampsite);
        campsites.add(campsite);
        return campsite;
    }

    @Override
    public void deleteCampsite(String id) {
        campsites.removeIf(campsite -> campsite.getId().toString().equals(id));
    }
}
