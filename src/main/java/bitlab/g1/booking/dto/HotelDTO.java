package bitlab.g1.booking.dto;

import bitlab.g1.booking.models.RoomType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class HotelDTO {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String address;
    private Double rating;
    private String image;
    private List<RoomType> roomTypes;
}
