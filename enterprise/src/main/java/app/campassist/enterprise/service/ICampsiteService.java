package app.campassist.enterprise.service;

import java.util.List;

import app.campassist.enterprise.dto.Campsite;

public interface ICampsiteService {
    /**
     * Fetch a campsite by its ID.
     * @param id The unique identifier of the campsite.
     * @return The campsite object if found, otherwise null.
     */
    Campsite fetchCampsiteById(String id);

    /**
     * Fetch all campsites.
     * @return A list of all campsite objects.
     */
    List<Campsite> fetchAllCampsites();

    /**
     * Add a new campsite.
     * @param campsite The campsite object to add.
     */
    void addCampsite(Campsite campsite);

    /**
     * Update an existing campsite.
     * @param campsite The campsite object with updated information.
     */
    void updateCampsite(Campsite campsite);

    /**
     * Delete a campsite by its ID.
     * @param id The unique identifier of the campsite.
     */
    void deleteCampsite(String id);
}
