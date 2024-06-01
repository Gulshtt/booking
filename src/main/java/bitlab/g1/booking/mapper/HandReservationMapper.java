package bitlab.g1.booking.mapper;

import bitlab.g1.booking.dto.HandReservationDTO;
import bitlab.g1.booking.models.HandReservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HandReservationMapper {
    HandReservationDTO toDto(HandReservation handReservation);
    HandReservation toEntity(HandReservationDTO handReservationDTO);
}
