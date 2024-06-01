package bitlab.g1.booking.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.Manager;

import java.util.Date;

@Data
@Entity
@Table(name = "hand_reservation")
public class HandReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private HotelManager manager;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date start_date;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date end_date;

    @Column(nullable = false, length = 255)
    private String guestFullName;

    @Column(nullable = false, length = 50)
    private String guestPhoneNumber;

    @Column(nullable = false, length = 255)
    private String guestEmail;

    @Column(nullable = false)
    private Boolean isActive = true;
}
