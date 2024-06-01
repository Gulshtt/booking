package bitlab.g1.booking.repositories;

import bitlab.g1.booking.models.HandReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HandReservationRepository extends JpaRepository<HandReservation, Long> {
    List<HandReservation> getHandReservationByManagerId(Long managerId);
}
