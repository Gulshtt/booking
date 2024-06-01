package bitlab.g1.booking.mapper;

import bitlab.g1.booking.dto.GuestDTO;
import bitlab.g1.booking.models.Guest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuestMapper {
    GuestDTO toDto(Guest guest);
    Guest toEntity(GuestDTO guestDTO);
}
