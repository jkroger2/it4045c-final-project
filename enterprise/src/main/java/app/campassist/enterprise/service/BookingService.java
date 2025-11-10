package app.campassist.enterprise.service;

import app.campassist.enterprise.dto.BookingDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
     * Fetch a list of bookings for a specific campsite.
     * @param campsiteId The identifier of the campsite to retrieve bookings for.
     * @return A list of bookings for the given campsite.
     */
    List<BookingDTO> fetchBookingsByCampsite(UUID campsiteId);

    /**
     * Fetch a list of bookings that have start or end date within a given time period.
     * @param startDate The start date of the time period to search.
     * @param endDate The end date of the time period to search.
     * @return A list of bookings that have start or end dates within the given time period. 
     */
    List<BookingDTO> fetchBookingsByDateRange(LocalDate startDate, LocalDate endDate);

    /**
     * Add a new booking.
     * @param booking The campsite object to add.
     * @return The added booking with any generated fields populated.
     */
    BookingDTO createBooking(BookingDTO booking) throws Exception;

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
