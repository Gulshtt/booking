package bitlab.g1.booking.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class AdminDTO {
    private Long id;
    private String fullName;
    private String email;
    private Date createdAt;
}
