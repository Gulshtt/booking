package bitlab.g1.booking.repositories;

import bitlab.g1.booking.models.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Guest findByEmail(String email);
}
