package app.campassist.enterprise.service.impl;

import app.campassist.enterprise.dto.BookingDTO;
import app.campassist.enterprise.mapper.BookingMapper;
import app.campassist.enterprise.model.Booking;
import app.campassist.enterprise.repository.BookingRepository;
import app.campassist.enterprise.service.BookingService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        BookingDTO booking = bookingRepository.findById(id)
                            .map(bookingMapper::toDTO)
                            .orElse(null);
        return booking;
    }

    @Override
    public List<BookingDTO> fetchBookingsByCampsite(UUID campsiteId) {
        List<BookingDTO> bookings = bookingRepository.findByCampsiteId(campsiteId)
                            .stream()
                            .map(bookingMapper::toDTO)
                            .toList();
        return bookings;
    }

    @Override 
    public List<BookingDTO> fetchBookingsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<BookingDTO> bookings = bookingRepository.findByStartDateAndEndDate(startDate, endDate)
                            .stream()
                            .map(bookingMapper::toDTO)
                            .toList();
        return bookings;
    }

    @Override
    public BookingDTO createBooking(BookingDTO dto) {


        // boolean bookingConflict = false;

        // // Check existing bookings for the campsite to ensure the chosen dates do not conflict with an existing booking
        // List<BookingDTO> existingCampsiteBookings = fetchBookingsByCampsite(dto.getCampsiteId());
        // if (!existingCampsiteBookings.isEmpty()) {
        //     for (BookingDTO existingBooking : existingCampsiteBookings) {
        //         if (
        //             bookingUtilities.isDateInRange(dto.getStartDate(), existingBooking.getStartDate(), existingBooking.getEndDate())
        //             || bookingUtilities.isDateInRange(dto.getEndDate(), existingBooking.getStartDate(), existingBooking.getEndDate())
        //         ) {
        //             bookingConflict = true;
        //         }
        //     }
        // }

        // // If bookingConflict exists, throw error
        // if (bookingConflict) {
        //     throw new Exception("Booking already exists within date range.");
        // }

        Booking booking = bookingMapper.toEntity(dto);
        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.toDTO(savedBooking);
    }

    @Override
    public BookingDTO updateBooking(BookingDTO dto) {
        Booking booking = bookingMapper.toEntity(dto);
        Booking updatedBooking = bookingRepository.save(booking);
        return bookingMapper.toDTO(updatedBooking);
    }

    @Override
    public void deleteBooking(UUID id) {
        bookingRepository.deleteById(id);
    }
}
