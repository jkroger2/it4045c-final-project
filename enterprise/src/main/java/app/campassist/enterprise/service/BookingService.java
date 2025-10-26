package app.campassist.enterprise.service;

import java.util.List;
import java.util.UUID;

import app.campassist.enterprise.dto.BookingDTO;

public interface BookingService {
    /**
     * Fetch all bookings.
     * @return
     */
    List<BookingDTO> fetchAllBookings();

    /**
     * Fetch a booking by its ID.
     * @param id The unique identifier of the booking.
     * @return The booking object if found, otherwise null.
     */
    BookingDTO fetchBookingById(UUID id);

    /**
     * Add a new booking.
     * @param booking The campsite object to add.
     * @return The added booking with any generated fields populated.
     */
    BookingDTO addBooking(BookingDTO booking);

    /**
     * Update an existing booking.
     * @param booking The booking object with updated information.
     * @return The updated booking object.
     */
    BookingDTO updateBooking(BookingDTO booking);

    /**
     * Delete a booking by its ID.
     * @param id The unique identifier of the booking.
     */
    void deleteBooking(UUID id);
}
