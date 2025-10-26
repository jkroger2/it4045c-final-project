package app.campassist.enterprise.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "campsites")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Campsite {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal maxRigLength;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerNight;

    @ElementCollection
    @CollectionTable(name = "campsite_amenities", joinColumns = @JoinColumn(name = "campsite_id"))
    @Column(name = "amenity")
    private List<String> amenities;

    @ElementCollection
    @CollectionTable(name = "campsite_images", joinColumns = @JoinColumn(name = "campsite_id"))
    @Column(name = "image_url")
    private List<String> images;

    @ElementCollection
    @CollectionTable(name = "campsite_tags", joinColumns = @JoinColumn(name = "campsite_id"))
    @Column(name = "tag")
    private List<String> tags;
}
