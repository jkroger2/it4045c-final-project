package app.campassist.enterprise.dto;

import java.math.BigDecimal;
import java.util.List;
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
class CampsiteDTO {
    private UUID id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    private String description;
    
    @NotBlank(message = "Address is required")
    private String address;
    
    @NotBlank(message = "City is required")
    private String city;
    
    @NotBlank(message = "State is required")
    private String state;
    
    @NotBlank(message = "Zip code is required")
    private String zipCode;
    
    @NotNull(message = "Max rig length is required")
    @DecimalMin(value = "0.0", message = "Max rig length must be positive")
    private BigDecimal maxRigLength;
    
    @NotNull(message = "Price per night is required")
    @DecimalMin(value = "0.0", message = "Price per night must be positive")
    private BigDecimal pricePerNight;
    
    private List<String> amenities;
    private List<String> images;
    private List<String> tags;
}