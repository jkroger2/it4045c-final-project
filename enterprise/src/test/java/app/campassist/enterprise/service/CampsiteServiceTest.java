package app.campassist.enterprise.service;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import app.campassist.enterprise.dto.CampsiteDTO;

@SpringBootTest
public class CampsiteServiceTest {

    @Autowired
    private CampsiteService campsiteService;

    @Test
    void contextLoads() {
    }

    /**
     * Test fetchAllCampsites returns a non-empty list of campsites
     */
    @Test
    void testFetchAllCampsites_returnsListOfCampsites() {
        List<CampsiteDTO> campsites = campsiteService.fetchAllCampsites();
        assert !campsites.isEmpty();
    }

    /**
     * Test fetchCampsiteById returns a campsite with the correct id
     */
    @Test
    void fetchCampsiteById_returnsCampsiteForId() {
        CampsiteDTO existingCampsite = campsiteService.fetchAllCampsites().get(0);
        UUID id = existingCampsite.getId();
        String name = existingCampsite.getName();

        CampsiteDTO campsite = campsiteService.fetchCampsiteById(id);
        assert campsite.getId().equals(id);
        assert campsite.getName().equals(name);
    }


    /**
     * Test addCampsite adds a campsite with given details
     */
    @Test
    void addCampsite_addsCampsiteWithGivenDetails() {
        CampsiteDTO dto = new CampsiteDTO();
        dto.setName("Test Campsite");

        CampsiteDTO campsite = campsiteService.createCampsite(dto);

        assert campsite.getName().equals(dto.getName());
    }

    /**
     * Test updateCampsite updates a campsite with given details
     */
    @Test
    void updateCampsite_updatesCampsiteWithGivenDetails() {
        CampsiteDTO dto = campsiteService.fetchAllCampsites().get(0);
        dto.setName("Updated Campsite");
        dto.setDescription("Updated Description");

        String id = dto.getId().toString();
        String name = dto.getName();
        String description = dto.getDescription();
        CampsiteDTO campsite = campsiteService.updateCampsite(dto);

        assert campsite.getId().toString().equals(id);
        assert campsite.getName().equals(name);
        assert campsite.getDescription().equals(description);
    }

    /**
     * Test deleteCampsite deletes a campsite with given id
     */
    @Test 
    void deleteCampsite_deletesCampsiteForId() {
        CampsiteDTO dto = campsiteService.fetchAllCampsites().get(0);
        UUID id = dto.getId();

        campsiteService.deleteCampsite(id);

        assert campsiteService.fetchCampsiteById(id) == null;

    }
}