package bitlab.g1.booking.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class HandReservationDTO {
    private Long id;
    private Long managerId;
    private Long roomId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start_date;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end_date;
    private String guestFullName;
    private String guestPhoneNumber;
    private String guestEmail;
    private Boolean isActive = true;

    private String roomNumber;
    private String roomTypeName;
}
