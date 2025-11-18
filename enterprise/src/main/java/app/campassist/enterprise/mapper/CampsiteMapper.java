package app.campassist.enterprise.mapper;

import app.campassist.enterprise.dto.CampsiteDTO;
import app.campassist.enterprise.model.Campsite;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CampsiteMapper {
    CampsiteDTO toDTO(Campsite entity);
    Campsite toEntity(CampsiteDTO dto);
}
