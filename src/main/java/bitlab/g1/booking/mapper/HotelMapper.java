package bitlab.g1.booking.mapper;

import bitlab.g1.booking.dto.HotelDTO;
import bitlab.g1.booking.dto.HotelCreateDTO;
import bitlab.g1.booking.models.Hotel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    HotelDTO toDto(Hotel hotel);
    Hotel toEntity(HotelDTO hotelDTO);
    Hotel toEntity(HotelCreateDTO hotelCreateDTO);
}
