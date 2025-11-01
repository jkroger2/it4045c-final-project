package app.campassist.enterprise.service;

import java.util.List;
import app.campassist.enterprise.dto.CampsiteDTO;

/**
 * Service interface for managing campsites.
 *
 * <p>Provides methods for creating, retrieving, updating, and deleting
 * {@link CampsiteDTO} objects. Implementations of this interface may
 * persist campsites in memory, in a database, or via other storage mechanisms.</p>
 */
public interface ICampsiteService {

    /**
     * Retrieves all campsites.
     *
     * @return a list of {@link CampsiteDTO} objects representing all campsites
     */
    List<CampsiteDTO> fetchAllCampsites();

    /**
     * Retrieves a campsite by its unique identifier.
     *
     * @param id the unique identifier of the campsite
     * @return the {@link CampsiteDTO} corresponding to the given ID, or {@code null} if not found
     */
    CampsiteDTO fetchCampsiteById(String id);

    /**
     * Adds a new campsite.
     *
     * <p>The returned {@link CampsiteDTO} may include generated fields, such as
     * a unique ID, that were not present in the input object.</p>
     *
     * @param campsite the {@link CampsiteDTO} to add
     * @return the added {@link CampsiteDTO} with generated fields populated
     */
    CampsiteDTO addCampsite(CampsiteDTO campsite);

    /**
     * Updates an existing campsite.
     *
     * <p>The campsite to update is identified by its ID field. The method replaces
     * the existing campsite's data with the values from the provided object.</p>
     *
     * @param campsite the {@link CampsiteDTO} containing updated campsite information
     * @return the updated {@link CampsiteDTO}
     */
    CampsiteDTO updateCampsite(CampsiteDTO campsite);

    /**
     * Deletes a campsite by its unique identifier.
     *
     * @param id the unique identifier of the campsite to delete
     */
    void deleteCampsite(String id);
}
