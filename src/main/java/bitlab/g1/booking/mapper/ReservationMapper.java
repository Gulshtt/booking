package bitlab.g1.booking.mapper;

import bitlab.g1.booking.dto.ReservationDTO;
import bitlab.g1.booking.models.Reservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationDTO toDto(Reservation reservation);
    Reservation toEntity(ReservationDTO reservationDTO);
}
