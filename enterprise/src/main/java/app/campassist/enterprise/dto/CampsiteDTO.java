package app.campassist.enterprise.dto;

import java.util.List;

import lombok.Data;

public @Data
class CampsiteDTO {
    private String id;
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