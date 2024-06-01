package bitlab.g1.booking.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RoomDTO {
    private Long id;
    private String number;
    private String description;
    private Boolean isSmoke;
    private Double pricePerDay;
    private String image;
    private String status;
    private Long roomTypeId;
}
