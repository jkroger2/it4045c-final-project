package app.campassist.enterprise.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import app.campassist.enterprise.dto.CampsiteDTO;

/**
 * Integration tests for {@link ICampsiteService}.
 *
 * <p>This test class uses {@link SpringBootTest} to load the full Spring
 * application context and autowire the {@link ICampsiteService} implementation.</p>
 *
 * <p>Tests include verifying that campsites can be fetched, added, updated,
 * and deleted correctly.</p>
 */
@SpringBootTest
public class CampsiteServiceTest {

    @Autowired
    private ICampsiteService campsiteService;

    /**
     * Test that the Spring application context loads successfully.
     */
    @Test
    void contextLoads() {
    }

    /**
     * Test that {@link ICampsiteService#fetchAllCampsites()} returns a non-empty list of campsites.
     */
    @Test
    void testFetchAllCampsites_returnsListOfCampsites() {
        List<CampsiteDTO> campsites = campsiteService.fetchAllCampsites();
        assert !campsites.isEmpty();
    }

    /**
     * Test that {@link ICampsiteService#fetchCampsiteById(String)} returns a campsite with the correct ID.
     */
    @Test
    void fetchCampsiteById_returnsCampsiteForId() {
        CampsiteDTO existingCampsite = campsiteService.fetchAllCampsites().get(0);
        String id = existingCampsite.getId().toString();
        String name = existingCampsite.getName();

        CampsiteDTO campsite = campsiteService.fetchCampsiteById(id);
        assert campsite.getId().toString().equals(id);
        assert campsite.getName().equals(name);
    }

    /**
     * Test that {@link ICampsiteService#addCampsite(CampsiteDTO)} adds a campsite with the given details.
     */
    @Test
    void addCampsite_addsCampsiteWithGivenDetails() {
        CampsiteDTO dto = new CampsiteDTO();
        dto.setName("Test Campsite");

        CampsiteDTO campsite = campsiteService.addCampsite(dto);

        assert campsite.getName().equals(dto.getName());
    }

    /**
     * Test that {@link ICampsiteService#updateCampsite(CampsiteDTO)} updates a campsite with the given details.
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
     * Test that {@link ICampsiteService#deleteCampsite(String)} deletes a campsite with the given ID.
     */
    @Test
    void deleteCampsite_deletesCampsiteForId() {
        CampsiteDTO dto = campsiteService.fetchAllCampsites().get(0);
        String id = dto.getId().toString();

        campsiteService.deleteCampsite(id);

        assert campsiteService.fetchCampsiteById(id) == null;
    }
}
