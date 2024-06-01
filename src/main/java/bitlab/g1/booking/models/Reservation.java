package bitlab.g1.booking.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date start_date;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date end_date;

    @Column(nullable = false)
    private Boolean isActive;
}
