package app.campassist.enterprise.service;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import app.campassist.enterprise.dto.BookingDTO;

@SpringBootTest
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @Test
    void contextLoads() {
    }

    /**
     * Test fetchAllBookings returns a non-empty list of bookings
     */
    @Test
    void testFetchAllBookings_returnsListOfBookings() {
        List<BookingDTO> bookings = bookingService.fetchAllBookings();
        assert !bookings.isEmpty();
    }

    /**
     * Test fetchBookingById returns a booking with the correct id
     */
    @Test
    void fetchBookingById_returnsBookingForId() {
        BookingDTO existingBooking = bookingService.fetchAllBookings().get(0);
        UUID id = existingBooking.getId();
        UUID userId = existingBooking.getUserId();

        BookingDTO booking = bookingService.fetchBookingById(id);
        assert booking.getId().equals(id);
        assert booking.getUserId().equals(userId);
    }

    /**
     * Test addBooking adds a booking with given details
     */
    @Test
    void addBooking_addsBookingWithGivenDetails() {
        BookingDTO dto = new BookingDTO();
        UUID userId = UUID.randomUUID();
        dto.setUserId(userId);

        BookingDTO booking = bookingService.addBooking(dto);

        assert booking.getUserId().toString().equals(dto.getUserId().toString());
    }

    /**
     * Test updateBooking updates a booking with given details
     */
    @Test
    void updateBooking_updatesBookingWithGivenDetails() {
        BookingDTO dto = bookingService.fetchAllBookings().get(0);
        dto.setEndDate(dto.getEndDate().plusDays(1));

        BookingDTO updatedBooking = bookingService.updateBooking(dto);

        assert updatedBooking.getEndDate().equals(dto.getEndDate().plusDays(1));
    }

    /**
     * Test deleteBooking deletes a booking with given id
     */
    @Test
    void deleteBooking_deletesBookingWithGivenId() {
        BookingDTO dto = bookingService.fetchAllBookings().get(0);
        UUID id = dto.getId();

        bookingService.deleteBooking(id);

        BookingDTO deletedBooking = bookingService.fetchBookingById(id);
        assert deletedBooking == null;
    }
}
