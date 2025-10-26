package app.campassist.enterprise.mapper;

import org.mapstruct.Mapper;

import app.campassist.enterprise.dto.BookingDTO;
import app.campassist.enterprise.model.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingDTO toDTO(Booking entity);
    Booking toEntity(BookingDTO dto);
}
