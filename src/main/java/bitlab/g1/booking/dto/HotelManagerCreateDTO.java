package bitlab.g1.booking.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class HotelManagerCreateDTO {
    private String fullName;
    private String email;
    private String password;
    private Long hotelId;
}
