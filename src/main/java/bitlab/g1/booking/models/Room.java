package bitlab.g1.booking.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Table(name = "rooms")
@Entity
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String number;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Boolean isSmoke;

    @Column(nullable = false)
    private Double pricePerDay;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column(nullable = false, length = 50)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomTypeId", nullable = false)
    private RoomType roomType;

    public Long getRoomTypeId() {
        return roomType != null ? roomType.getId() : null;
    }

    public void setRoomTypeId(Long roomTypeId) {
        if (roomTypeId != null) {
            this.roomType = new RoomType();
            this.roomType.setId(roomTypeId);
        } else {
            this.roomType = null;
        }
    }
}
