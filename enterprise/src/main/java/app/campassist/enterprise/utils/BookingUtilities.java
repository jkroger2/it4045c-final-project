package app.campassist.enterprise.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BookingUtilities {
    
    /**
     * Checks if a given date is within a date range.
     * @param date The date to check.
     * @param dateRangeStart The start of the date range.
     * @param dateRangeEnd The end of the date range.
     * @return True if the date is within the date range, False if it is not within the date range.
     */
    public boolean isDateInRange(LocalDate date, LocalDate dateRangeStart, LocalDate dateRangeEnd) {
        return (
            (date.isAfter(dateRangeStart) || date.isEqual(dateRangeStart)) && 
            (date.isBefore(dateRangeEnd) || date.isEqual(dateRangeEnd))
        );
    }
}
