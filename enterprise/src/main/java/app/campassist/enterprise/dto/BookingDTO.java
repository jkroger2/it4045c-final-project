package app.campassist.enterprise.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing a booking in the CampAssist system.
 *
 * <p>This class is used to transfer booking data between the service and controller layers.</p>
 *
 * <p>Fields include booking details, associated campsite ID, customer information,
 * booking period, total cost, and booking status.</p>
 *
 * <p>Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor) are used to
 * automatically generate getters, setters, constructors, equals, hashCode, and toString methods.</p>
 *
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {

    /** Unique identifier for the booking. */
    private UUID id;

    /** Identifier of the campsite associated with the booking. */
    private UUID campsiteId;

    /** Customer's first name. */
    private String firstName;

    /** Customer's last name. */
    private String lastName;

    /** Customer's email address. */
    private String email;

    /** Customer's phone number. */
    private String phoneNumber;

    /** Start date of the booking. */
    private LocalDate startDate;

    /** End date of the booking. */
    private LocalDate endDate;

    /** Total amount for the booking. */
    private double total;

    /** Status of the booking (e.g., confirmed, cancelled). */
    private String status;
}
