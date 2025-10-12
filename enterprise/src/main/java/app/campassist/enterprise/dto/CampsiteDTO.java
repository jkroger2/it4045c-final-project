package app.campassist.enterprise.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data
@NoArgsConstructor
@AllArgsConstructor
class CampsiteDTO {
    private UUID id;
    private String name;
    private String description;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private double latitude;
    private double longitude;
    private List<String> amenities;
    private List<String> images;
    private List<String> tags;
}