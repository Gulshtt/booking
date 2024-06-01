package bitlab.g1.booking.repositories;

import bitlab.g1.booking.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findBySlug(String slug); // SELECT * FROM hotel WHERE hotel.slug == String slug
    List<Hotel> findByAddress(String addres); // SELECT * FROM hotel WHERE hotel.address == String addres
    @Query("SELECT DISTINCT h.address FROM Hotel h")
    List<String> findDistinctAddresses();
}
