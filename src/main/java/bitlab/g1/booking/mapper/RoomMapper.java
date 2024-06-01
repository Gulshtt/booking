package bitlab.g1.booking.mapper;

import bitlab.g1.booking.dto.RoomDTO;
import bitlab.g1.booking.models.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomDTO toDto(Room room);
    Room toEntity(RoomDTO roomDTO);
}
