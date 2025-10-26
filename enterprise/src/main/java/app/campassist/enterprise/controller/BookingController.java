package app.campassist.enterprise.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.campassist.enterprise.dto.BookingDTO;
import app.campassist.enterprise.service.BookingService
;

@Controller
@RequestMapping("/api/bookings/")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * GET /api/bookings/
     */
    @GetMapping("/")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.fetchAllBookings();
        return ResponseEntity.ok(bookings);
    }

    /**
     * GET /api/bookings/{id}/
     */
    @GetMapping("/{id}/")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable String id) {
        BookingDTO booking = bookingService.fetchBookingById(UUID.fromString(id));
        return ResponseEntity.ok(booking);
    }

    /**
     * POST /api/bookings/
     */
    @PostMapping(value="/", consumes="application/json", produces="application/json")
    public ResponseEntity<BookingDTO> addBooking(@RequestBody BookingDTO booking) {
        BookingDTO newBooking = bookingService.addBooking(booking);
        return ResponseEntity.ok(newBooking);
    }

    /**
     * PUT /api/bookings/{id}/
     */
    @PutMapping(value="/{id}/", consumes="application/json", produces="application/json")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable String id, @RequestBody BookingDTO booking) {
        UUID bookingId = UUID.fromString(id);
        booking.setId(bookingId);
        BookingDTO updatedBooking = bookingService.updateBooking(booking);
        return ResponseEntity.ok(updatedBooking);
    }

    /**
     * DELETE /api/bookings/{id}/
     */
    @DeleteMapping("/{id}/")
    public ResponseEntity<Void> deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }
}