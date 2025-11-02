package app.campassist.enterprise.unit.service;

import app.campassist.enterprise.dto.CampsiteDTO;
import app.campassist.enterprise.mapper.CampsiteMapper;
import app.campassist.enterprise.model.Campsite;
import app.campassist.enterprise.repository.CampsiteRepository;
import app.campassist.enterprise.service.impl.CampsiteServiceImpl;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CampsiteServiceTest {

    @Mock
    private CampsiteRepository campsiteRepository;

    @Mock
    private CampsiteMapper campsiteMapper;

    @InjectMocks
    private CampsiteServiceImpl campsiteService;

    @Test
    void contextLoads() {
    }

    /**
     * Test fetchAllCampsites returns a non-empty list of campsites
     */
    @Test
    void testFetchAllCampsites_returnsListOfCampsites() {
        when(campsiteRepository.findAll()).thenReturn(List.of(
            new Campsite(
                UUID.randomUUID(),
                "Campsite 1",
                "Description 1",
                "Address 1",
                "City 1",
                "State 1",
                "Zip 1",
                BigDecimal.valueOf(20.00),
                BigDecimal.valueOf(15.00),
                null,
                null,
                null
            ),
            new Campsite(
                UUID.randomUUID(),
                "Campsite 2",
                "Description 2",
                "Address 2",
                "City 2",
                "State 2",
                "Zip 2",
                BigDecimal.valueOf(25.00),
                BigDecimal.valueOf(20.00),
                null,
                null,
                null
            )
        ));
        
        List<CampsiteDTO> campsites = campsiteService.fetchAllCampsites();
        
        assert !campsites.isEmpty();
    }

    /**
     * Test fetchCampsiteById returns a campsite with the correct id
     */
    @Test
    void fetchCampsiteById_returnsCampsiteForId() {
        UUID id = UUID.randomUUID();
        
        Campsite entity = new Campsite();
        entity.setId(id);

        CampsiteDTO dto = new CampsiteDTO();
        dto.setId(id);

        when(campsiteRepository.findById(any(UUID.class))).thenReturn(Optional.of(entity)); 
        when(campsiteMapper.toDTO(any(Campsite.class))).thenReturn(dto);
        
        CampsiteDTO campsite = campsiteService.fetchCampsiteById(id);
        
        assert campsite.getId().equals(id);
    }


    /**
     * Test addCampsite adds a campsite with given details
     */
    @Test
    void addCampsite_addsCampsiteWithGivenDetails() {

        CampsiteDTO dto = new CampsiteDTO();
        dto.setName("Test Campsite");
        dto.setDescription("Test Description");
        dto.setAddress("123 Test St");
        dto.setCity("Test City");
        dto.setState("TS");
        dto.setZipCode("12345");
        dto.setMaxRigLength(BigDecimal.valueOf(30.00));
        dto.setPricePerNight(BigDecimal.valueOf(25.00));
        dto.setAmenities(null);
        dto.setImages(null);
        dto.setTags(null);

        Campsite entity = new Campsite();
        entity.setId(UUID.randomUUID());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setAddress(dto.getAddress());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setZipCode(dto.getZipCode());
        entity.setMaxRigLength(dto.getMaxRigLength());
        entity.setPricePerNight(dto.getPricePerNight());
        entity.setAmenities(null);
        entity.setImages(null);
        entity.setTags(null);

        when(campsiteMapper.toEntity(any(CampsiteDTO.class))).thenReturn(entity);
        when(campsiteRepository.save(any(Campsite.class))).thenReturn(entity);
        when(campsiteMapper.toDTO(any(Campsite.class))).thenReturn(dto);

        CampsiteDTO campsite = campsiteService.createCampsite(dto);

        assert campsite == dto;
    }

    /**
     * Test updateCampsite updates a campsite with given details
     */
    @Test
    void updateCampsite_updatesCampsiteWithGivenDetails() {

        UUID id = UUID.randomUUID();

        CampsiteDTO dto = new CampsiteDTO();
        dto.setId(id);
        dto.setName("Updated Campsite");
        dto.setDescription("Updated Description");

        Campsite entity = new Campsite();
        entity.setId(id);
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        when(campsiteMapper.toEntity(any(CampsiteDTO.class))).thenReturn(entity);
        when(campsiteRepository.save(any(Campsite.class))).thenReturn(entity);
        when(campsiteMapper.toDTO(any(Campsite.class))).thenReturn(dto);

        CampsiteDTO campsite = campsiteService.updateCampsite(dto);

        assert campsite == dto;
    }

    /**
     * Test deleteCampsite deletes a campsite with given id
     */
    @Test 
    void deleteCampsite_deletesCampsiteForId() {
        doNothing().when(campsiteRepository).deleteById(any(UUID.class));

        UUID id = UUID.randomUUID();
        campsiteService.deleteCampsite(id);

        verify(campsiteRepository).deleteById(id);
    }
}