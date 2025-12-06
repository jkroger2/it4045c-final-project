package app.campassist.enterprise.web.controller;

import app.campassist.enterprise.dto.CampsiteDTO;
import app.campassist.enterprise.dto.BookingDTO;
import app.campassist.enterprise.service.BookingService;
import app.campassist.enterprise.service.CampsiteService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Controller
@RequestMapping("/")
public class WebController {

    private final CampsiteService campsiteService;
    private final BookingService bookingService;

    public WebController(CampsiteService campsiteService, BookingService bookingService) {
        this.campsiteService = campsiteService;
        this.bookingService = bookingService;
    }

    @GetMapping("/campsites")
    public String showCampsitesList(Model model) {
        model.addAttribute("campsites", campsiteService.fetchAllCampsites());
        return "campsites/list";
    }

    @GetMapping("/campsites/{id}/details")
    public String showCampsiteDetails(@PathVariable String id, Model model) {
        UUID campsiteId = UUID.fromString(id);
        model.addAttribute("campsite", campsiteService.fetchCampsiteById(campsiteId));
        return "campsites/details";
    }

    @GetMapping("/campsites/{id}/book")
    public String showBookingForm(@PathVariable String id, Model model) {
        UUID campsiteId = UUID.fromString(id);
        model.addAttribute("campsite", campsiteService.fetchCampsiteById(campsiteId));
        return "campsites/book";
    }

    @PostMapping("/campsites/{id}/book")
    public String submitBooking(@PathVariable String id,
                                @RequestParam String email,
                                @RequestParam String startDate,
                                @RequestParam String endDate,
                                Model model) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        
        UUID campsiteId = UUID.fromString(id);
        CampsiteDTO campsite = campsiteService.fetchCampsiteById(campsiteId);                       
        BookingDTO booking = new BookingDTO();
        booking.setCampsiteId(campsiteId);
        booking.setCampsiteName(campsite.getName());
        booking.setEmail(email);
        booking.setStartDate(start);
        booking.setEndDate(end);
        

        long daysBetween = ChronoUnit.DAYS.between(start, end);
        BigDecimal total = campsite.getPricePerNight().multiply(BigDecimal.valueOf(daysBetween));
        booking.setTotal(total);
        booking.setStatus("CONFIRMED");

        booking = bookingService.createBooking(booking);

        model.addAttribute("booking", booking);
        model.addAttribute("campsite", campsite);

        return "campsites/booking-confirmation";
    }

    @GetMapping("/bookings/{email}")
    public String showUserBookings(@PathVariable String email, Model model) {
        model.addAttribute("bookings", bookingService.fetchBookingsByEmail(email));
        model.addAttribute("email", email);
        
        return "bookings/list";
    }

    @GetMapping("/bookings/{id}/details")
    public String showBookingDetails(@PathVariable String id, Model model) {
        UUID bookingId = UUID.fromString(id);

        CampsiteDTO campsite = campsiteService.fetchCampsiteById(bookingService.fetchBookingById(bookingId).getCampsiteId());
        
        model.addAttribute("booking", bookingService.fetchBookingById(bookingId));
        model.addAttribute("campsite", campsite);
        
        return "bookings/details";
    }
                            
}
