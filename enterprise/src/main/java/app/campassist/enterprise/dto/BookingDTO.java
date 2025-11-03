package app.campassist.enterprise.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    @NotNull private UUID campsiteId;
    @NotNull private UUID userId;
    @NotNull @FutureOrPresent private LocalDate startDate;
    @NotNull @Future private LocalDate endDate;
    @NotNull @Positive private BigDecimal total;
    @NotNull private String status;
}
