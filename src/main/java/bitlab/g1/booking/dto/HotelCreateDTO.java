package bitlab.g1.booking.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class HotelCreateDTO {
    private String name;
    private String slug;
    private String description;
    private String address;
    private Double rating;
    private String image;
}
