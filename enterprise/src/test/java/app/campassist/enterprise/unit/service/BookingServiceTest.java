package app.campassist.enterprise.unit.service;

import app.campassist.enterprise.dto.BookingDTO;
import app.campassist.enterprise.mapper.BookingMapper;
import app.campassist.enterprise.model.Booking;
import app.campassist.enterprise.repository.BookingRepository;
import app.campassist.enterprise.service.impl.BookingServiceImpl;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    void contextLoads() {
    }

    /**
     * Test fetchAllBookings returns a non-empty list of bookings
     */
    @Test
    void testFetchAllBookings_returnsListOfBookings() {
        when(bookingRepository.findAll()).thenReturn(List.of(
            new Booking(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                null,
                null,
                BigDecimal.valueOf(2.00),
                null
            ),
            new Booking(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                null,
                null,
                BigDecimal.valueOf(4.00),
                null
            )
        ));

        List<BookingDTO> bookings = bookingService.fetchAllBookings();
        
        assert !bookings.isEmpty();
    }

    /**
     * Test fetchBookingById returns a booking with the correct id
     */
    @Test
    void fetchBookingById_returnsBookingForId() {
        UUID id = UUID.randomUUID();

        Booking entity = new Booking();
        entity.setId(id);

        BookingDTO dto = new BookingDTO();
        dto.setId(id);

        when(bookingRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(entity)); 
        when(bookingMapper.toDTO(any(Booking.class))).thenReturn(dto);

        BookingDTO booking = bookingService.fetchBookingById(id);
        
        assert booking.getId().equals(id);
    }

    /**
     * Test addBooking adds a booking with given details
     */
    @Test
    void addBooking_addsBookingWithGivenDetails() {
        
        BookingDTO dto = new BookingDTO();
        dto.setCampsiteId(UUID.randomUUID());
        dto.setUserId(UUID.randomUUID());
        dto.setStartDate(null);
        dto.setEndDate(null);
        dto.setTotal(BigDecimal.valueOf(10.00));
        dto.setStatus("CONFIRMED");

        Booking entity = new Booking();
        entity.setId(UUID.randomUUID());
        entity.setCampsiteId(dto.getCampsiteId());
        entity.setUserId(dto.getUserId());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setTotal(dto.getTotal());
        entity.setStatus(dto.getStatus());

        when(bookingMapper.toEntity(any(BookingDTO.class))).thenReturn(entity);
        when(bookingRepository.save(any(Booking.class))).thenReturn(entity);
        when(bookingMapper.toDTO(any(Booking.class))).thenReturn(dto);

        BookingDTO booking = bookingService.createBooking(dto);

        assert booking == dto;
    }

    /**
     * Test updateBooking updates a booking with given details
     */
    @Test
    void updateBooking_updatesBookingWithGivenDetails() {

        UUID id = UUID.randomUUID();

        BookingDTO dto = new BookingDTO();
        dto.setId(id);
        dto.setEndDate(null);
        dto.setTotal(BigDecimal.valueOf(15.00));

        Booking entity = new Booking();
        entity.setId(id);
        entity.setEndDate(dto.getEndDate());
        entity.setTotal(dto.getTotal());

        when(bookingMapper.toEntity(any(BookingDTO.class))).thenReturn(entity);
        when(bookingRepository.save(any(Booking.class))).thenReturn(entity);
        when(bookingMapper.toDTO(any(Booking.class))).thenReturn(dto);

        BookingDTO booking = bookingService.updateBooking(dto);

        assert booking == dto;
    }

    /**
     * Test deleteBooking deletes a booking with given id
     */
    @Test
    void deleteBooking_deletesBookingWithGivenId() {
        doNothing().when(bookingRepository).deleteById(any(UUID.class));

        UUID id = UUID.randomUUID();
        bookingService.deleteBooking(id);

        verify(bookingRepository).deleteById(id);
    }
}
