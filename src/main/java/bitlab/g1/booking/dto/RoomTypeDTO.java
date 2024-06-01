package bitlab.g1.booking.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RoomTypeDTO {
    private Long id;
    private String name;
    private String description;
    private Integer maxCapacity;
    private Long hotelId;
}
