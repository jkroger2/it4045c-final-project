package app.campassist.enterprise.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.campassist.enterprise.dto.BookingDTO;
import app.campassist.enterprise.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * GET /api/bookings/
     */
    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.fetchAllBookings();
        return ResponseEntity.ok(bookings);
    }

    /**
     * GET /api/bookings/{id}/
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable UUID id) {
        BookingDTO booking = bookingService.fetchBookingById(id);
        if (booking == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(booking);
    }

    /**
     * POST /api/bookings
     */
    @PostMapping(consumes="application/json", produces="application/json")
    public ResponseEntity<BookingDTO> addBooking(@RequestBody BookingDTO booking) {
        BookingDTO newBooking = bookingService.addBooking(booking);
        HttpHeaders headers = new HttpHeaders();
        if (newBooking.getId() != null) {
            headers.add(HttpHeaders.LOCATION, "/api/bookings/" + newBooking.getId());
        }
        return new ResponseEntity<>(newBooking, headers, HttpStatus.CREATED);
    }

    /**
     * PUT /api/bookings/{id}
     */
    @PutMapping(value="/{id}/", consumes="application/json", produces="application/json")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable UUID id, @RequestBody BookingDTO booking) {
        booking.setId(id);
        BookingDTO updatedBooking = bookingService.updateBooking(booking);
        return ResponseEntity.ok(updatedBooking);
    }

    /**
     * DELETE /api/bookings/{id}
     */
@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable UUID id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}