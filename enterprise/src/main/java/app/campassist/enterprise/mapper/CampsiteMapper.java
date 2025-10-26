package app.campassist.enterprise.mapper;

import org.mapstruct.Mapper;

import app.campassist.enterprise.dto.CampsiteDTO;
import app.campassist.enterprise.model.Campsite;

@Mapper(componentModel = "spring")
public interface CampsiteMapper {
    CampsiteDTO toDTO(Campsite entity);
    Campsite toEntity(CampsiteDTO dto);
}
