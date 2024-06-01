package bitlab.g1.booking.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class GuestDTO {
    private Long id;
    private String fullName;
    private String email;
    private String location;
    private String phoneNumber;
    private Date createdAt;
}
