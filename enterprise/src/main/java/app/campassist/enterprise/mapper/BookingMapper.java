package app.campassist.enterprise.mapper;

import app.campassist.enterprise.dto.BookingDTO;
import app.campassist.enterprise.model.Booking;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingDTO toDTO(Booking entity);
    Booking toEntity(BookingDTO dto);
}
