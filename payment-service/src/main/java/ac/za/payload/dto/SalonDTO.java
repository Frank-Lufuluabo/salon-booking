package ac.za.payload.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SalonDTO {

    private Long id;

    private String name;

    private List<String> images;

    private String address;

    private String phoneNumber;

    private String email;

    private String city;

    private Long ownerId;

    private LocalDateTime openTime;

    private LocalDateTime closeTime;
}
