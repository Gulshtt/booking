package bitlab.g1.booking.repositories;

import bitlab.g1.booking.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByRoomType_Id(Long roomTypeId);
}
