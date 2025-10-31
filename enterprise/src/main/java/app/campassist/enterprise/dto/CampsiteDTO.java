package app.campassist.enterprise.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing a campsite in the CampAssist system.
 *
 * <p>This class is used to transfer campsite data between the service and controller layers.</p>
 *
 * <p>Fields include campsite details such as name, location, pricing, capacity, and
 * associated lists like amenities, images, and tags.</p>
 *
 * <p>Lombok annotations (@Data, @NoArgsConstructor, @AllArgsConstructor) are used to
 * automatically generate getters, setters, constructors, equals, hashCode, and toString methods.</p>
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampsiteDTO {

    /** Unique identifier for the campsite. */
    private UUID id;

    /** Name of the campsite. */
    private String name;

    /** Description of the campsite. */
    private String description;

    /** Street address of the campsite. */
    private String address;

    /** City where the campsite is located. */
    private String city;

    /** State where the campsite is located. */
    private String state;

    /** ZIP code of the campsite location. */
    private String zipCode;

    /** Maximum rig length (e.g., RV or trailer) allowed at the campsite. */
    private double maxRigLength;

    /** Price per night for booking the campsite. */
    private double pricePerNight;

    /** List of amenities available at the campsite (e.g., WiFi, showers). */
    private List<String> amenities;

    /** List of image URLs associated with the campsite. */
    private List<String> images;

    /** List of tags for categorizing or labeling the campsite (e.g., "pet-friendly"). */
    private List<String> tags;
}
