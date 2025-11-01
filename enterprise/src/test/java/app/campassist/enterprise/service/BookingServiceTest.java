package app.campassist.enterprise.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import app.campassist.enterprise.dto.BookingDTO;

/**
 * Integration tests for {@link IBookingService}.
 *
 * <p>This test class uses {@link SpringBootTest} to load the full Spring
 * application context and autowire the {@link IBookingService} implementation.</p>
 *
 * <p>Tests include verifying that bookings can be fetched, added, updated,
 * and deleted correctly.</p>
 */
@SpringBootTest
public class BookingServiceTest {

    @Autowired
    private IBookingService bookingService;

    /**
     * Test that the Spring application context loads successfully.
     */
    @Test
    void contextLoads() {
    }

    /**
     * Test that {@link IBookingService#fetchAllBookings()} returns a non-empty list of bookings.
     */
    @Test
    void testFetchAllBookings_returnsListOfBookings() {
        List<BookingDTO> bookings = bookingService.fetchAllBookings();
        assert !bookings.isEmpty();
    }

    /**
     * Test that {@link IBookingService#fetchBookingById(String)} returns a booking with the correct ID.
     */
    @Test
    void fetchBookingById_returnsBookingForId() {
        BookingDTO existingBooking = bookingService.fetchAllBookings().get(0);
        String id = existingBooking.getId().toString();
        String firstName = existingBooking.getFirstName();

        BookingDTO booking = bookingService.fetchBookingById(id);
        assert booking.getId().toString().equals(id);
        assert booking.getFirstName().equals(firstName);
    }

    /**
     * Test that {@link IBookingService#addBooking(BookingDTO)} adds a booking with the given details.
     */
    @Test
    void addBooking_addsBookingWithGivenDetails() {
        BookingDTO dto = new BookingDTO();
        dto.setFirstName("Test");
        dto.setLastName("User");

        BookingDTO booking = bookingService.addBooking(dto);

        assert booking.getFirstName().equals(dto.getFirstName());
        assert booking.getLastName().equals(dto.getLastName());
    }

    /**
     * Test that {@link IBookingService#updateBooking(BookingDTO)} updates a booking with the given details.
     */
    @Test
    void updateBooking_updatesBookingWithGivenDetails() {
        BookingDTO dto = bookingService.fetchAllBookings().get(0);
        dto.setFirstName("UpdatedName");

        BookingDTO updatedBooking = bookingService.updateBooking(dto);

        assert updatedBooking.getFirstName().equals("UpdatedName");
    }

    /**
     * Test that {@link IBookingService#deleteBooking(String)} deletes a booking with the given ID.
     */
    @Test
    void deleteBooking_deletesBookingWithGivenId() {
        BookingDTO dto = bookingService.fetchAllBookings().get(0);
        String id = dto.getId().toString();

        bookingService.deleteBooking(id);

        BookingDTO deletedBooking = bookingService.fetchBookingById(id);
        assert deletedBooking == null;
    }
}
