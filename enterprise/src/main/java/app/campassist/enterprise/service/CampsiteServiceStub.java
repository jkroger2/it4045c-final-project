package app.campassist.enterprise.service;

import java.util.List;

import org.springframework.stereotype.Component;

import app.campassist.enterprise.dto.CampsiteDTO;

@Component
public class CampsiteServiceStub implements ICampsiteService {
    @Override
    public List<CampsiteDTO> fetchAllCampsites() {
        List<CampsiteDTO> campsites = List.of(new CampsiteDTO());
        return campsites;
    }

    @Override
    public CampsiteDTO fetchCampsiteById(String id) {
        CampsiteDTO campsite = new CampsiteDTO();
        campsite.setId(id);
        campsite.setName("Sample Campsite");

        return campsite;
    }

    @Override
    public CampsiteDTO addCampsite(CampsiteDTO campsite) {
        return campsite;
    }

    @Override
    public CampsiteDTO updateCampsite(CampsiteDTO campsite) {
        return campsite;
    }

    @Override
    public void deleteCampsite(String id) {
    }
}
