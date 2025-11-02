package app.campassist.enterprise.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import app.campassist.enterprise.dto.CampsiteDTO;
import app.campassist.enterprise.service.CampsiteService;

@Controller
@RequestMapping("/api/campsites/")
public class CampsiteController {

    private final CampsiteService campsiteService;

    public CampsiteController(CampsiteService campsiteService) {
        this.campsiteService = campsiteService;
    }

    /**
     * GET /api/campsites/
     */
    @GetMapping("/")
    public ResponseEntity<List<CampsiteDTO>> getAllCampsites() {
        List<CampsiteDTO> campsites = campsiteService.fetchAllCampsites();
        return ResponseEntity.ok(campsites);
    }

    /**
     * GET /api/campsites/{id}/
     */
    @GetMapping("/{id}/")
    public ResponseEntity<CampsiteDTO> getCampsiteById(@PathVariable String id) {
        CampsiteDTO campsite = campsiteService.fetchCampsiteById(UUID.fromString(id));
        return ResponseEntity.ok(campsite);
    }
    
    /**
     * POST /api/campsites/
     */
    @PostMapping("/")
    public ResponseEntity<CampsiteDTO> createCampsite(@Valid @RequestBody CampsiteDTO campsite) {
        CampsiteDTO newCampsite = campsiteService.createCampsite(campsite);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCampsite);
    }

    /**
     * PUT /api/campsites/{id}/
     */
    @PutMapping("/{id}/")
    public ResponseEntity<CampsiteDTO> updateCampsite(@PathVariable String id, @Valid @RequestBody CampsiteDTO campsite) {
        UUID campsiteId = UUID.fromString(id);
        campsite.setId(campsiteId);
        CampsiteDTO updatedCampsite = campsiteService.updateCampsite(campsite);
        return ResponseEntity.ok(updatedCampsite);
    }

    /**
     * DELETE /api/campsites/{id}/
     */
    @DeleteMapping("/{id}/")
    public ResponseEntity<Void> deleteCampsite(@PathVariable String id) {
        campsiteService.deleteCampsite(UUID.fromString(id));
        return ResponseEntity.ok().build();
    }
}
