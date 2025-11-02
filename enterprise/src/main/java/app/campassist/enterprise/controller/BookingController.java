package app.campassist.enterprise.controller;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.campassist.enterprise.dto.BookingDTO;
import app.campassist.enterprise.service.IBookingService;

// ahart21 suggestions
// REST improvements: use clean route paths (no trailing slashes) and @RestController for JSON APIs.
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * GET /api/bookings
     */
    // No path -> maps to /api/bookings
    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.fetchAllBookings();
        return ResponseEntity.ok(bookings);
    }

    /**
     * GET /api/bookings/{id}
     */
    // Use typed UUID for the @PathVariable so Spring will validate/convert the id automatically.
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable UUID id) {
        BookingDTO booking = bookingService.fetchBookingById(id.toString());
        return ResponseEntity.ok(booking);
    }

    /**
     * POST /api/bookings
     */
    // Return 201 Created with Location header pointing to the new resource (REST best practice).
    @PostMapping(consumes="application/json", produces="application/json")
    public ResponseEntity<BookingDTO> addBooking(@RequestBody BookingDTO booking) {
        BookingDTO newBooking = bookingService.addBooking(booking);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(newBooking.getId())
            .toUri();
        return ResponseEntity.created(location).body(newBooking);
    }

    /**
     * PUT /api/bookings/{id}
     */
    @PutMapping(value="/{id}", consumes="application/json", produces="application/json")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable UUID id, @RequestBody BookingDTO booking) {
        // Use the parsed UUID directly rather than parsing from String inside the method.
        booking.setId(id);
        BookingDTO updatedBooking = bookingService.updateBooking(booking);
        return ResponseEntity.ok(updatedBooking);
    }

    /**
     * DELETE /api/bookings/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable UUID id) {
        bookingService.deleteBooking(id.toString());
        return ResponseEntity.noContent().build();
    }
}