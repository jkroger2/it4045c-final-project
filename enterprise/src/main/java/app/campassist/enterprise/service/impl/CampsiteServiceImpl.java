package app.campassist.enterprise.service.impl;

import app.campassist.enterprise.dto.CampsiteDTO;
import app.campassist.enterprise.mapper.CampsiteMapper;
import app.campassist.enterprise.model.Campsite;
import app.campassist.enterprise.repository.CampsiteRepository;
import app.campassist.enterprise.service.CampsiteService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Profile("!mock")
public class CampsiteServiceImpl implements CampsiteService {

    private final CampsiteRepository campsiteRepository;
    private final CampsiteMapper campsiteMapper;

    public CampsiteServiceImpl(CampsiteRepository campsiteRepository, CampsiteMapper campsiteMapper) {
        this.campsiteRepository = campsiteRepository;
        this.campsiteMapper = campsiteMapper;
    }

    @Override
    public List<CampsiteDTO> fetchAllCampsites() {
        List<CampsiteDTO> campsites = campsiteRepository.findAll()
                            .stream()
                            .map(campsiteMapper::toDTO)
                            .toList();
        return campsites;
    }

    @Override
    public CampsiteDTO fetchCampsiteById(UUID id) {
        CampsiteDTO campsite = campsiteRepository.findById(id)
                                .map(campsiteMapper::toDTO)
                                .orElse(null);
        return campsite;
    }

    @Override
    public CampsiteDTO createCampsite(CampsiteDTO dto) {
        Campsite campsite = campsiteMapper.toEntity(dto);
        Campsite savedCampsite = campsiteRepository.save(campsite);
        return campsiteMapper.toDTO(savedCampsite);
    }

    @Override
    public CampsiteDTO updateCampsite(CampsiteDTO dto) {
        Campsite campsite = campsiteMapper.toEntity(dto);
        Campsite updatedCampsite = campsiteRepository.save(campsite);
        return campsiteMapper.toDTO(updatedCampsite);
    }

    @Override
    public void deleteCampsite(UUID id) {
        campsiteRepository.deleteById(id);
    }   
}
