package app.campassist.enterprise.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import app.campassist.enterprise.dto.CampsiteDTO;

/**
 * A stub implementation of the {@link ICampsiteService} interface.
 *
 * <p>This service provides in-memory operations for {@link CampsiteDTO} objects.
 * It is intended for testing and demonstration purposes and does not persist data
 * to a database.</p>
 *
 * <p>The service maintains a private list of campsites and allows the following operations:</p>
 * <ul>
 *     <li>Fetch all campsites</li>
 *     <li>Fetch a campsite by ID</li>
 *     <li>Add a new campsite</li>
 *     <li>Update an existing campsite</li>
 *     <li>Delete a campsite by ID</li>
 * </ul>
 *
 * <p>Sample data is initialized in the constructor to simulate a real repository.</p>
 */
@Service
public class CampsiteServiceStub implements ICampsiteService {

    private final List<CampsiteDTO> campsites = new ArrayList<>();

    /**
     * Constructs the service and populates it with sample in-memory campsite data.
     */
    public CampsiteServiceStub() {
        // In-memory stub data
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
                        List.of("https://images.pexels.com/photos/1309587/pexels-photo-1309587.jpeg"),
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

    /**
     * Returns a list of all campsites.
     *
     * @return a list of {@link CampsiteDTO} objects
     */
    @Override
    public List<CampsiteDTO> fetchAllCampsites() {
        return campsites;
    }

    /**
     * Fetches a campsite by its unique ID.
     *
     * @param id the UUID of the campsite as a string
     * @return the matching {@link CampsiteDTO}, or {@code null} if not found
     */
    @Override
    public CampsiteDTO fetchCampsiteById(String id) {
        return campsites.stream()
                .filter(campsite -> campsite.getId().toString().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds a new campsite to the in-memory list.
     *
     * @param dto the campsite data to add
     * @return the newly created {@link CampsiteDTO} with a generated UUID
     */
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

    /**
     * Updates an existing campsite in the in-memory list.
     *
     * @param campsite the updated campsite data
     * @return the updated {@link CampsiteDTO}
     */
    @Override
    public CampsiteDTO updateCampsite(CampsiteDTO campsite) {
        CampsiteDTO existingCampsite = fetchCampsiteById(campsite.getId().toString());
        campsites.remove(existingCampsite);
        campsites.add(campsite);
        return campsite;
    }

    /**
     * Deletes a campsite by its unique ID.
     *
     * @param id the UUID of the campsite as a string
     */
    @Override
    public void deleteCampsite(String id) {
        campsites.removeIf(campsite -> campsite.getId().toString().equals(id));
    }
}
