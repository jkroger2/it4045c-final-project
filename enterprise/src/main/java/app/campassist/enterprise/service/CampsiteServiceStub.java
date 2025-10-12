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
                39.7817,
                89.6501,
                List.of(
                    "WiFi",
                    "Restrooms"
                ),
                List.of(
                    "https://www.pexels.com/photo/foggy-mountain-above-a-campsite-2873086/"
                ),
                List.of(
                    "family-friendly", "pet-friendly"
                )
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
                33.0369,
                117.2920,
                List.of(
                    "Boat Ramp",
                    "Fishing Dock"
                ),
                List.of(
                    "https://www.pexels.com/photo/aerial-view-of-green-trees-near-a-campsite-4993949/"
                ),
                List.of(
                    "waterfront", "nature"
                )
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
            dto.getLatitude(),
            dto.getLongitude(),
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
