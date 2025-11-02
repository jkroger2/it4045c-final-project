package app.campassist.enterprise.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data
@NoArgsConstructor
@AllArgsConstructor
class BookingDTO {
    private UUID id;
    
    @NotNull(message = "Campsite ID is required")
    private UUID campsiteId;
    
    @NotNull(message = "User ID is required")
    private UUID userId;
    
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    
    @NotNull(message = "End date is required")
    private LocalDate endDate;
    
    @NotNull(message = "Total is required")
    @DecimalMin(value = "0.0", message = "Total must be positive")
    private BigDecimal total;
    
    @NotBlank(message = "Status is required")
    private String status;
}
