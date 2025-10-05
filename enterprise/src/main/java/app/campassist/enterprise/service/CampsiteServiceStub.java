package app.campassist.enterprise.service;

import java.util.List;

import org.springframework.stereotype.Component;

import app.campassist.enterprise.dto.Campsite;

@Component
public class CampsiteServiceStub implements ICampsiteService {
    @Override
    public Campsite fetchCampsiteById(String id) {
        Campsite campsite = new Campsite();
        campsite.setId(id);

        return campsite;
    }

    
    @Override
    public List<Campsite> fetchAllCampsites() {
        List<Campsite> campsites = List.of(new Campsite());
        return campsites;
    }


    @Override
    public void addCampsite(Campsite campsite) {
    }


    @Override
    public void updateCampsite(Campsite campsite) {
    }


    @Override
    public void deleteCampsite(String id) {
    }
}
