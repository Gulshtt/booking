package bitlab.g1.booking.service;

import bitlab.g1.booking.dto.HotelDTO;
import bitlab.g1.booking.mapper.HotelMapperImpl;
import bitlab.g1.booking.models.Hotel;
import bitlab.g1.booking.repositories.HotelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapperImpl hotelMapper;

    public List<HotelDTO> getAllHotels() {
        return hotelRepository.findAll().stream().map(hotelMapper::toDto).toList();
    }

    public List<HotelDTO> getHotelsByCity(String addr) {
        return hotelRepository.findByAddress(addr).stream().map(hotelMapper::toDto).collect(Collectors.toList());
    }

    public HotelDTO getHotelBySlug(String slug) {
        Hotel hotel = hotelRepository.findBySlug(slug).orElseThrow(() -> new RuntimeException("Hotel not found with slug: " + slug));
        return hotelMapper.toDto(hotel);
    }

    public void createHotel(HotelDTO hotelDTO) {
        hotelRepository.save(hotelMapper.toEntity(hotelDTO));
    }

    @Transactional
    public void updateHotel(HotelDTO hotelDTO) {
        HotelDTO existingHotel = hotelMapper.toDto(hotelRepository.findById(hotelDTO.getId())
                .orElseThrow(() -> new RuntimeException("Hotel not found")));

        existingHotel.setName(hotelDTO.getName());
        existingHotel.setAddress(hotelDTO.getAddress());
        existingHotel.setDescription(hotelDTO.getDescription());
        existingHotel.setRating(hotelDTO.getRating());
        existingHotel.setSlug(hotelDTO.getSlug());
        existingHotel.setImage(hotelDTO.getImage());

        hotelRepository.save(hotelMapper.toEntity(existingHotel));
    }

    public List<String> getLocations() {
        return hotelRepository.findDistinctAddresses();
    }

    public HotelDTO getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new RuntimeException("Hotel not found"));
        return hotelMapper.toDto(hotel);
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }
}
