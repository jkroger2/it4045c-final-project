package app.campassist.enterprise.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ahart21 suggestions
// Make the DTO class public so it's accessible across packages and frameworks like Jackson can
// instantiate it for JSON (de)serialization.
// Lombok annotations must appear directly above the class declaration.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private UUID id;
    private UUID campsiteId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private double total;
    private String status;
}
