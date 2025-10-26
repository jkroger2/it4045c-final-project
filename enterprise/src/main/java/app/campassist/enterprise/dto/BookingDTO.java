package app.campassist.enterprise.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data
@NoArgsConstructor
@AllArgsConstructor
class BookingDTO {
    private UUID id;
    private UUID campsiteId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String startDate;
    private String endDate;
    private double total;
    private String status;
}
