package app.campassist.enterprise.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.campassist.enterprise.dto.CampsiteDTO;
import app.campassist.enterprise.service.ICampsiteService;

/**
 * REST controller for managing campsites in the CampAssist system.
 * Provides CRUD operations for campsite resources via HTTP endpoints.
 *
 * <p>Base URL: /api/campsites</p>
 *
 * <p>Supported operations:
 * <ul>
 *     <li>GET /api/campsites - List all campsites</li>
 *     <li>GET /api/campsites/{id} - Retrieve a campsite by ID</li>
 *     <li>POST /api/campsites - Create a new campsite</li>
 *     <li>PUT /api/campsites/{id} - Update an existing campsite</li>
 *     <li>DELETE /api/campsites/{id} - Delete a campsite by ID</li>
 * </ul>
 * </p>
 *
 * @author
 */
@RestController
@RequestMapping("/api/campsites")
public class CampsiteController {

    private final ICampsiteService campsiteService;

    /**
     * Constructs a new CampsiteController with the provided campsite service.
     *
     * @param campsiteService the service used to manage campsites
     */
    public CampsiteController(ICampsiteService campsiteService) {
        this.campsiteService = campsiteService;
    }

    /**
     * Retrieves a list of all campsites.
     *
     * @return ResponseEntity containing a list of CampsiteDTO objects
     */
    @GetMapping
    public ResponseEntity<List<CampsiteDTO>> getAllCampsites() {
        List<CampsiteDTO> campsites = campsiteService.fetchAllCampsites();
        return ResponseEntity.ok(campsites);
    }

    /**
     * Retrieves a single campsite by its ID.
     *
     * @param id the ID of the campsite to retrieve
     * @return ResponseEntity containing the CampsiteDTO object
     */
    @GetMapping("/{id}")
    public ResponseEntity<CampsiteDTO> getCampsiteById(@PathVariable String id) {
        CampsiteDTO campsite = campsiteService.fetchCampsiteById(id);
        return ResponseEntity.ok(campsite);
    }

    /**
     * Creates a new campsite.
     *
     * @param campsite the campsite information to create
     * @return ResponseEntity containing the newly created CampsiteDTO
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<CampsiteDTO> addCampsite(@RequestBody CampsiteDTO campsite) {
        CampsiteDTO newCampsite = campsiteService.addCampsite(campsite);
        return ResponseEntity.ok(newCampsite);
    }

    /**
     * Updates an existing campsite identified by its ID.
     *
     * @param id the ID of the campsite to update
     * @param campsite the updated campsite information
     * @return ResponseEntity containing the updated CampsiteDTO
     */
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CampsiteDTO> updateCampsite(@PathVariable String id, @RequestBody CampsiteDTO campsite) {
        UUID campsiteId = UUID.fromString(id);
        campsite.setId(campsiteId);
        CampsiteDTO updatedCampsite = campsiteService.updateCampsite(campsite);
        return ResponseEntity.ok(updatedCampsite);
    }

    /**
     * Deletes a campsite by its ID.
     *
     * @param id the ID of the campsite to delete
     * @return ResponseEntity with HTTP status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampsite(@PathVariable String id) {
        campsiteService.deleteCampsite(id);
        return ResponseEntity.noContent().build();
    }
}
