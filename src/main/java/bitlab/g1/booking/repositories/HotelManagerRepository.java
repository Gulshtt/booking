package bitlab.g1.booking.repositories;

import bitlab.g1.booking.models.HotelManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelManagerRepository extends JpaRepository<HotelManager, Long> {
    List<HotelManager> findByHotelId(Long hotelId);

    Optional<HotelManager> findByEmail(String email);
}
