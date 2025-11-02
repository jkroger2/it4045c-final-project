package app.campassist.enterprise.dto;

import java.math.BigDecimal;
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
    private UUID userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal total;
    private String status;
}
