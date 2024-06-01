package bitlab.g1.booking.service;

import bitlab.g1.booking.dto.HotelDTO;
import bitlab.g1.booking.dto.HotelManagerDTO;
import bitlab.g1.booking.mapper.HotelManagerMapper;
import bitlab.g1.booking.mapper.HotelMapper;
import bitlab.g1.booking.models.Hotel;
import bitlab.g1.booking.models.HotelManager;
import bitlab.g1.booking.repositories.HotelManagerRepository;
import bitlab.g1.booking.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelManagerRepository hotelManagerRepository;

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private HotelManagerMapper hotelManagerMapper;

    public HotelDTO createHotel(HotelDTO hotelDTO) {
        Hotel hotel = hotelMapper.toEntity(hotelDTO);
        hotel = hotelRepository.save(hotel);
        return hotelMapper.toDto(hotel);
    }

    public HotelManagerDTO createHotelManager(HotelManagerDTO hotelManagerDTO) {
        HotelManager hotelManager = hotelManagerMapper.toEntity(hotelManagerDTO);
        hotelManager = hotelManagerRepository.save(hotelManager);
        return hotelManagerMapper.toDto(hotelManager);
    }
}
