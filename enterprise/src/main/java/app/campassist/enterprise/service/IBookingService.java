package app.campassist.enterprise.service;

import java.util.List;
import app.campassist.enterprise.dto.BookingDTO;

/**
 * Service interface for managing bookings.
 *
 * <p>Provides methods for creating, retrieving, updating, and deleting
 * {@link BookingDTO} objects. Implementations of this interface may
 * persist bookings in memory, in a database, or via other storage mechanisms.</p>
 */
public interface IBookingService {

    /**
     * Retrieves all bookings.
     *
     * @return a list of {@link BookingDTO} objects representing all bookings
     */
    List<BookingDTO> fetchAllBookings();

    /**
     * Retrieves a booking by its unique identifier.
     *
     * @param id the unique identifier of the booking
     * @return the {@link BookingDTO} corresponding to the given ID, or {@code null} if not found
     */
    BookingDTO fetchBookingById(String id);

    /**
     * Adds a new booking.
     *
     * <p>The returned {@link BookingDTO} may include generated fields, such as
     * a unique ID, that were not present in the input object.</p>
     *
     * @param booking the {@link BookingDTO} to add
     * @return the added {@link BookingDTO} with generated fields populated
     */
    BookingDTO addBooking(BookingDTO booking);

    /**
     * Updates an existing booking.
     *
     * <p>The booking to update is identified by its ID field. The method replaces
     * the existing booking's data with the values from the provided object.</p>
     *
     * @param booking the {@link BookingDTO} containing updated booking information
     * @return the updated {@link BookingDTO}
     */
    BookingDTO updateBooking(BookingDTO booking);

    /**
     * Deletes a booking by its unique identifier.
     *
     * @param id the unique identifier of the booking to delete
     */
    void deleteBooking(String id);
}
