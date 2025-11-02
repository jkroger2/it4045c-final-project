package app.campassist.enterprise.service.impl;

import app.campassist.enterprise.dto.BookingDTO;
import app.campassist.enterprise.mapper.BookingMapper;
import app.campassist.enterprise.model.Booking;
import app.campassist.enterprise.repository.BookingRepository;
import app.campassist.enterprise.service.BookingService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Profile("!mock")
public class BookingServiceImpl implements BookingService{

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    
    public BookingServiceImpl(BookingRepository bookingRepository, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public List<BookingDTO> fetchAllBookings() {
        List<BookingDTO> bookings = bookingRepository.findAll()
                            .stream()
                            .map(bookingMapper::toDTO)
                            .toList();
        return bookings;
    }

    @Override
    public BookingDTO fetchBookingById(UUID id) {
        return bookingRepository.findById(id)
                            .map(bookingMapper::toDTO)
                            .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
    }

    @Override
    public BookingDTO addBooking(BookingDTO dto) {
        Booking booking = bookingMapper.toEntity(dto);
        return bookingMapper.toDTO(bookingRepository.save(booking));
    }

    @Override
    public BookingDTO updateBooking(BookingDTO dto) {
        Booking booking = bookingMapper.toEntity(dto);
        return bookingMapper.toDTO(bookingRepository.save(booking));
    }

    @Override
    public void deleteBooking(UUID id) {
        bookingRepository.deleteById(id);
    }
}
