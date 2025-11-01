package app.campassist.enterprise.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import app.campassist.enterprise.dto.BookingDTO;

/**
 * A stub implementation of the {@link IBookingService} interface.
 *
 * <p>This service provides in-memory operations for {@link BookingDTO} objects.
 * It is intended for testing and demonstration purposes and does not persist data
 * to a database.</p>
 *
 * <p>The service maintains a private list of bookings and allows the following operations:</p>
 * <ul>
 *     <li>Fetch all bookings</li>
 *     <li>Fetch a booking by ID</li>
 *     <li>Add a new booking</li>
 *     <li>Update an existing booking</li>
 *     <li>Delete a booking by ID</li>
 * </ul>
 *
 * <p>Sample booking data is initialized in the constructor to simulate a real repository.</p>
 */
@Service
public class BookingServiceStub implements IBookingService {

    private final List<BookingDTO> bookings = new ArrayList<>();

    /**
     * Constructs the service and populates it with sample in-memory booking data.
     */
    public BookingServiceStub() {
        // In-memory stub data
        bookings.add(
                new BookingDTO(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        "John",
                        "Doe",
                        "jdoe@gmail.com",
                        "513-000-1111",
                        LocalDate.of(2025, 11, 25),
                        LocalDate.of(2025, 11, 28),
                        250.00,
                        "CONFIRMED"
                )
        );
        bookings.add(
                new BookingDTO(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        "Jane",
                        "Smith",
                        "jsmith@gmail.com",
                        "513-000-2222",
                        LocalDate.of(2025, 12, 5),
                        LocalDate.of(2025, 12, 10),
                        500.00,
                        "CANCELLED"
                )
        );
    }

    /**
     * Returns a list of all bookings.
     *
     * @return a list of {@link BookingDTO} objects
     */
    @Override
    public List<BookingDTO> fetchAllBookings() {
        return bookings;
    }

    /**
     * Fetches a booking by its unique ID.
     *
     * @param id the UUID of the booking as a string
     * @return the matching {@link BookingDTO}, or {@code null} if not found
     */
    @Override
    public BookingDTO fetchBookingById(String id) {
        // Check if id is null or empty
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Booking ID cannot be null or empty.");
        }

        // Attempt to find booking by ID, otherwise throw custom exception
        return bookings.stream()
                .filter(booking -> booking.getId().toString().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookingNotFoundException("Booking with ID " + id + " not found"));
    }

    /**
     * Adds a new booking to the in-memory list.
     *
     * @param dto the booking data to add
     * @return the newly created {@link BookingDTO} with a generated UUID
     */
    @Override
    public BookingDTO addBooking(BookingDTO dto) {
        // Null check for BookingDTO and essential fields
        if (dto == null || dto.getFirstName() == null || dto.getLastName() == null || dto.getEmail() == null) {
            throw new IllegalArgumentException("Booking information is incomplete.");
        }

        // Create a new booking with UUID and other provided details
        BookingDTO newBooking = new BookingDTO(
                UUID.randomUUID(),
                dto.getCampsiteId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPhoneNumber(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getTotal(),
                dto.getStatus()
        );
        bookings.add(newBooking);
        return newBooking;
    }

    /**
     * Updates an existing booking in the in-memory list.
     *
     * @param booking the updated booking data
     * @return the updated {@link BookingDTO}
     */
    @Override
    public BookingDTO updateBooking(BookingDTO booking) {
        // Fetch the existing booking and update it
        BookingDTO existingBooking = fetchBookingById(booking.getId().toString());
        bookings.remove(existingBooking);
        bookings.add(booking);
        return booking;
    }

    /**
     * Deletes a booking by its unique ID.
     *
     * @param id the UUID of the booking as a string
     */
    @Override
    public void deleteBooking(String id) {
        bookings.removeIf(booking -> booking.getId().toString().equals(id));
    }
}
