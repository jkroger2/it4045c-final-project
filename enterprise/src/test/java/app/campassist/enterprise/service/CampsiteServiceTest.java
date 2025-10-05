package app.campassist.enterprise.service;

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
    
    @Test
    void contextLoads() {
    }

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
}
