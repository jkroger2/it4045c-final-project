package app.campassist.enterprise.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ahart21 suggestions
// Make the DTO class public so it's accessible from other packages (services, controllers, tests)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampsiteDTO {
    private UUID id;
    private String name;
    private String description;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private double maxRigLength;
    private double pricePerNight;
    private List<String> amenities;
    private List<String> images;
    private List<String> tags;
}