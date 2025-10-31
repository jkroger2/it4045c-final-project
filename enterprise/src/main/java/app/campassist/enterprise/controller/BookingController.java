package app.campassist.enterprise.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.campassist.enterprise.dto.BookingDTO;
import app.campassist.enterprise.service.IBookingService;

/**
 * REST controller for managing bookings in the CampAssist system.
 * Provides CRUD operations for booking resources via HTTP endpoints.
 *
 * <p>Base URL: /api/bookings</p>
 *
 * <p>Supported operations:
 * <ul>
 *     <li>GET /api/bookings - List all bookings</li>
 *     <li>GET /api/bookings/{id} - Retrieve a booking by ID</li>
 *     <li>POST /api/bookings - Create a new booking</li>
 *     <li>PUT /api/bookings/{id} - Update an existing booking</li>
 *     <li>DELETE /api/bookings/{id} - Delete a booking by ID</li>
 * </ul>
 * </p>
 *
 * @author
 */
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final IBookingService bookingService;

    /**
     * Constructs a new BookingController with the provided booking service.
     *
     * @param bookingService the service used to manage bookings
     */
    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Retrieves a list of all bookings.
     *
     * @return ResponseEntity containing a list of BookingDTO objects
     */
    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.fetchAllBookings();
        return ResponseEntity.ok(bookings);
    }

    /**
     * Retrieves a single booking by its ID.
     *
     * @param id the ID of the booking to retrieve
     * @return ResponseEntity containing the BookingDTO object
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable String id) {
        BookingDTO booking = bookingService.fetchBookingById(id);
        return ResponseEntity.ok(booking);
    }

    /**
     * Creates a new booking.
     *
     * @param booking the booking information to create
     * @return ResponseEntity containing the newly created BookingDTO
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<BookingDTO> addBooking(@RequestBody BookingDTO booking) {
        BookingDTO newBooking = bookingService.addBooking(booking);
        return ResponseEntity.ok(newBooking);
    }

    /**
     * Updates an existing booking identified by its ID.
     *
     * @param id the ID of the booking to update
     * @param booking the updated booking information
     * @return ResponseEntity containing the updated BookingDTO
     */
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable String id, @RequestBody BookingDTO booking) {
        UUID bookingId = UUID.fromString(id);
        booking.setId(bookingId);
        BookingDTO updatedBooking = bookingService.updateBooking(booking);
        return ResponseEntity.ok(updatedBooking);
    }

    /**
     * Deletes a booking by its ID.
     *
     * @param id the ID of the booking to delete
     * @return ResponseEntity with HTTP status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
