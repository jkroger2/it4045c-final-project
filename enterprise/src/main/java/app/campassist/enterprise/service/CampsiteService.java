package app.campassist.enterprise.service;

import app.campassist.enterprise.dto.CampsiteDTO;

import java.util.List;
import java.util.UUID;

public interface CampsiteService {
    /**
     * Fetch all campsites.
     * @return A list of all campsite objects.
     */
    List<CampsiteDTO> fetchAllCampsites();

    /**
     * Fetch a campsite by its ID.
     * @param id The unique identifier of the campsite.
     * @return The campsite object if found, otherwise null.
     */
    CampsiteDTO fetchCampsiteById(UUID id);

    /**
     * Creates a new campsite.
     * @param campsite The campsite object to add.
     * @return The added campsite with any generated fields populated.
     */
    CampsiteDTO createCampsite(CampsiteDTO campsite);

    /**
     * Update an existing campsite.
     * @param campsite The campsite object with updated information.
     * @return The updated campsite object.
     */
    CampsiteDTO updateCampsite(CampsiteDTO campsite);

    /**
     * Delete a campsite by its ID.
     * @param id The unique identifier of the campsite.
     */
    void deleteCampsite(UUID id);
}
