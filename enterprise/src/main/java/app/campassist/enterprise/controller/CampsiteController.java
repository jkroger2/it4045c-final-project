package app.campassist.enterprise.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.campassist.enterprise.dto.CampsiteDTO;
import app.campassist.enterprise.service.ICampsiteService;

@Controller
@RequestMapping("/api/campsites")
public class CampsiteController {

    private final ICampsiteService campsiteService;

    public CampsiteController(ICampsiteService campsiteService) {
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
        CampsiteDTO campsite = campsiteService.fetchCampsiteById(id);
        return ResponseEntity.ok(campsite);
    }
    
    /**
     * POST /api/campsites/
     */
    @PostMapping(value="/", consumes="application/json", produces="application/json")
    public ResponseEntity<CampsiteDTO> addCampsite(@RequestBody CampsiteDTO campsite) {
        CampsiteDTO newCampsite = campsiteService.addCampsite(campsite);
        return ResponseEntity.ok(newCampsite);
    }

    /**
     * PUT /api/campsites/{id}/
     */
    @PutMapping(value="/{id}/", consumes="application/json", produces="application/json")
    public ResponseEntity<CampsiteDTO> updateCampsite(@PathVariable String id, @RequestBody CampsiteDTO campsite) {
        campsite.setId(id);
        CampsiteDTO updatedCampsite = campsiteService.updateCampsite(campsite);
        return ResponseEntity.ok(updatedCampsite);
    }

    /**
     * DELETE /api/campsites/{id}/
     */
    @DeleteMapping("/{id}/")
    public ResponseEntity<Void> deleteCampsite(@PathVariable String id) {
        campsiteService.deleteCampsite(id);
        return ResponseEntity.ok().build();
    }
    
}
