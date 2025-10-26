package app.campassist.enterprise.service;

import java.util.List;

import app.campassist.enterprise.dto.CampsiteDTO;

public interface ICampsiteService {
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
    CampsiteDTO fetchCampsiteById(String id);

    /**
     * Add a new campsite.
     * @param campsite The campsite object to add.
     * @return The added campsite with any generated fields populated.
     */
    CampsiteDTO addCampsite(CampsiteDTO campsite);

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
    void deleteCampsite(String id);
}
