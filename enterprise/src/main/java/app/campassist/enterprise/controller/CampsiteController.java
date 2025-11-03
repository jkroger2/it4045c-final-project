package app.campassist.enterprise.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.campassist.enterprise.dto.CampsiteDTO;
import app.campassist.enterprise.service.CampsiteService;

@RestController
@RequestMapping("/api/campsites")
public class CampsiteController {

    private final CampsiteService campsiteService;

    public CampsiteController(CampsiteService campsiteService) {
        this.campsiteService = campsiteService;
    }

    /**
     * GET /api/campsites/
     */
    @GetMapping
    public ResponseEntity<List<CampsiteDTO>> getAllCampsites() {
        List<CampsiteDTO> campsites = campsiteService.fetchAllCampsites();
        return ResponseEntity.ok(campsites);
    }

    /**
     * GET /api/campsites/{id}/
     */
    @GetMapping("/{id}")
    public ResponseEntity<CampsiteDTO> getCampsiteById(@PathVariable UUID id) {
        CampsiteDTO campsite = campsiteService.fetchCampsiteById(id);
        if (campsite == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(campsite);
    }
    
    /**
     * POST /api/campsites/
     */
    @PostMapping(consumes="application/json", produces="application/json")
    public ResponseEntity<CampsiteDTO> createCampsite(@RequestBody CampsiteDTO campsite) {
        CampsiteDTO newCampsite = campsiteService.createCampsite(campsite);
         HttpHeaders headers = new HttpHeaders();
        if (newCampsite.getId() != null) {
            headers.add(HttpHeaders.LOCATION, "/api/campsites/" + newCampsite.getId());
        }
        return new ResponseEntity<>(newCampsite, headers, HttpStatus.CREATED);
    }

    /**
     * PUT /api/campsites/{id}/
     */
    @PutMapping(value="/{id}", consumes="application/json", produces="application/json")
    public ResponseEntity<CampsiteDTO> updateCampsite(@PathVariable UUID id, @RequestBody CampsiteDTO campsite) {
        campsite.setId(id);
        CampsiteDTO updatedCampsite = campsiteService.updateCampsite(campsite);
        return ResponseEntity.ok(updatedCampsite);
    }

    /**
     * DELETE /api/campsites/{id}/
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampsite(@PathVariable UUID id) {
        campsiteService.deleteCampsite(id);
        return ResponseEntity.noContent().build();
    }
}
