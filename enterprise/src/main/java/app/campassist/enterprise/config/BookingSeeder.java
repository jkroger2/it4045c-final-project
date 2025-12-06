package app.campassist.enterprise.config;

import app.campassist.enterprise.repository.BookingRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class BookingSeeder implements CommandLineRunner {

    private final BookingRepository bookingRepository;

    public BookingSeeder(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void run(String... args) {
        bookingRepository.deleteAll();
    }
}
