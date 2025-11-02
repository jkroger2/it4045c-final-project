package app.campassist.enterprise.controller;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.campassist.enterprise.dto.CampsiteDTO;
import app.campassist.enterprise.service.CampsiteService;

// ahart21 suggestions
// REST improvements: use clean route paths (no trailing slashes) and @RestController for JSON APIs.
@RestController
@RequestMapping("/api/campsites")
public class CampsiteController {

    private final CampsiteService campsiteService;

    public CampsiteController(CampsiteService campsiteService) {
        this.campsiteService = campsiteService;
    }

    /**
     * GET /api/campsites
     */
    @GetMapping
    public ResponseEntity<List<CampsiteDTO>> getAllCampsites() {
        List<CampsiteDTO> campsites = campsiteService.fetchAllCampsites();
        return ResponseEntity.ok(campsites);
    }

    /**
     * GET /api/campsites/{id}
     */
    // Use typed UUID for the @PathVariable so Spring will validate/convert the id automatically.
    @GetMapping("/{id}")
    public ResponseEntity<CampsiteDTO> getCampsiteById(@PathVariable UUID id) {
        CampsiteDTO campsite = campsiteService.fetchCampsiteById(id.toString());
        return ResponseEntity.ok(campsite);
    }
    
    /**
     * POST /api/campsites
     */
    // Return 201 Created with Location header pointing to the new resource (REST best practice).
    @PostMapping(consumes="application/json", produces="application/json")
    public ResponseEntity<CampsiteDTO> addCampsite(@RequestBody CampsiteDTO campsite) {
        CampsiteDTO newCampsite = campsiteService.addCampsite(campsite);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newCampsite.getId())
            .toUri();
        return ResponseEntity.created(location).body(newCampsite);
    }

    /**
     * PUT /api/campsites/{id}
     */
    @PutMapping(value="/{id}", consumes="application/json", produces="application/json")
    public ResponseEntity<CampsiteDTO> updateCampsite(@PathVariable UUID id, @RequestBody CampsiteDTO campsite) {
        campsite.setId(id);
        CampsiteDTO updatedCampsite = campsiteService.updateCampsite(campsite);
        return ResponseEntity.ok(updatedCampsite);
    }

    /**
     * DELETE /api/campsites/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampsite(@PathVariable UUID id) {
        campsiteService.deleteCampsite(id.toString());
        return ResponseEntity.noContent().build();
    }
}
