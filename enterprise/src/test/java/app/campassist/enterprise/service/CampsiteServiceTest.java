package app.campassist.enterprise.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import app.campassist.enterprise.dto.Campsite;

@SpringBootTest
public class CampsiteServiceTest {

    @Autowired
    private ICampsiteService campsiteService;
    private Campsite campsite;
    private List<Campsite> allCampsites;
    

    @Test
    void contextLoads() {
    }


    /**
     * Test fetchCampsiteById returns a campsite with the correct id
     */
    @Test
    void fetchCampsiteById_returnsCampsiteForId() {
        String id = "00000000-0000-0000-0000-000000000000";

        whenSearchCampsiteWithId(id);
        thenReturnCampsiteWithId(id);
    }

    private void whenSearchCampsiteWithId(String id) {
        campsite = campsiteService.fetchCampsiteById(id);
    }

    private void thenReturnCampsiteWithId(String id) {
        assertEquals(id, campsite.getId());
        assertEquals(id, campsite.getId());
    }


    /**
     * Test fetchAllCampsites returns a non-empty list of campsites
     */
    @Test
    void fetchAllCampsites_returnsListOfCampsites() {
        
        whenFetchAllCampsites();
        thenReturnNonEmptyListOfCampsites();
    }

    private void whenFetchAllCampsites() {
        allCampsites = campsiteService.fetchAllCampsites();
    }

    private void thenReturnNonEmptyListOfCampsites() {
        assert !allCampsites.isEmpty();
    }


    /**
     * Test addCampsite adds a campsite with given details
     */
    @Test
    void addCampsite_addsCampsiteWithGivenDetails() {
        Campsite newCampsite = new Campsite();
        newCampsite.setId("00000000-0000-0000-0000-000000000000");
        newCampsite.setName("New Campsite");
        newCampsite.setDescription("New Description");

        whenAddCampsiteWithDetails(newCampsite);
        thenCampsiteExistsWithDetails(newCampsite);
    }

    private void whenAddCampsiteWithDetails(Campsite newCampsite) {
        campsiteService.addCampsite(newCampsite);
    }

    private void thenCampsiteExistsWithDetails(Campsite newCampsite) {
        campsite = campsiteService.fetchCampsiteById(newCampsite.getId());
        assertEquals(newCampsite.getId(), campsite.getId());
    }


    /**
     * Test updateCampsite updates a campsite with given details
     */
    @Test
    void updateCampsite_updatesCampsiteWithGivenDetails() {
        Campsite updatedCampsite = new Campsite();
        updatedCampsite.setId("00000000-0000-0000-0000-000000000000");
        updatedCampsite.setName("Updated Campsite");
        updatedCampsite.setDescription("Updated Description");

        whenUpdateCampsiteWithDetails(updatedCampsite);
        thenCampsiteExistsWithNewDetails(updatedCampsite);
    }

    private void whenUpdateCampsiteWithDetails(Campsite updatedCampsite) {
        campsiteService.updateCampsite(updatedCampsite);
    }

    private void thenCampsiteExistsWithNewDetails(Campsite updatedCampsite) {
        campsite = campsiteService.fetchCampsiteById(updatedCampsite.getId());
        assertEquals(updatedCampsite, campsite);
    }

    /**
     * Test deleteCampsite deletes a campsite with given id
     */
    @Test 
    void deleteCampsite_deletesCampsiteForId() {
        String id = "00000000-0000-0000-0000-000000000000";

        whenDeleteCampsiteWithId(id);
        thenCampsiteDoesNotExist(id);
    }

    private void whenDeleteCampsiteWithId(String id) {
        campsiteService.deleteCampsite(id);
    }

    private void thenCampsiteDoesNotExist(String id) {
        campsite = campsiteService.fetchCampsiteById(id);
        assertEquals(null, campsite);
    }
}