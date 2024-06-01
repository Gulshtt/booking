package bitlab.g1.booking.mapper;

import bitlab.g1.booking.dto.HotelManagerDTO;
import bitlab.g1.booking.dto.HotelManagerCreateDTO;
import bitlab.g1.booking.models.HotelManager;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelManagerMapper {
    HotelManagerDTO toDto(HotelManager hotelManager);
    HotelManager toEntity(HotelManagerDTO hotelManagerDTO);
    HotelManager toEntity(HotelManagerCreateDTO hotelManagerCreateDTO);
}
