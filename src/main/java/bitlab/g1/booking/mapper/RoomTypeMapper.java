package bitlab.g1.booking.mapper;

import bitlab.g1.booking.dto.RoomTypeDTO;
import bitlab.g1.booking.models.RoomType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomTypeMapper {
    RoomTypeDTO toDto(RoomType roomType);
    RoomType toEntity(RoomTypeDTO roomTypeDTO);
}
