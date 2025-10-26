package app.campassist.enterprise.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import app.campassist.enterprise.dto.BookingDTO;

@Service
public class BookingServiceStub implements IBookingService {
    private final List<BookingDTO> bookings = new ArrayList<>();

    public BookingServiceStub() {
        // In memory stub data
        bookings.add(
            new BookingDTO(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDate.of(2025, 11, 25),
                LocalDate.of(2025, 11, 28),
                new BigDecimal(250.00),
                "CONFIRMED"
            )
        );
        bookings.add(
            new BookingDTO(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDate.of(2025, 12, 5),
                LocalDate.of(2025, 12, 10),
                new BigDecimal(400.00),
                "CANCELLED"
            )
        );
    }

    @Override
    public List<BookingDTO> fetchAllBookings() {
        return bookings;
    }

    @Override
    public BookingDTO fetchBookingById(String id) {
        return bookings.stream()
            .filter(booking -> booking.getId().toString().equals(id))
            .findFirst()
            .orElse(null);
    }

    @Override
    public BookingDTO addBooking(BookingDTO dto) {
        BookingDTO newBooking = new BookingDTO(
            UUID.randomUUID(),
            dto.getCampsiteId(),
            dto.getUserId(),
            dto.getStartDate(),
            dto.getEndDate(),
            dto.getTotal(),
            dto.getStatus()
        );
        bookings.add(newBooking);
        return newBooking;
    }

    @Override
    public BookingDTO updateBooking(BookingDTO booking) {
        BookingDTO existingBooking = fetchBookingById(booking.getId().toString());
        bookings.remove(existingBooking);
        bookings.add(booking);
        return booking;
    }

    @Override
    public void deleteBooking(String id) {
        bookings.removeIf(booking -> booking.getId().toString().equals(id));
    }
}
