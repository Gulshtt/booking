package bitlab.g1.booking.service;

import bitlab.g1.booking.dto.RoomTypeDTO;
import bitlab.g1.booking.mapper.RoomMapperImpl;
import bitlab.g1.booking.mapper.RoomTypeMapperImpl;
import bitlab.g1.booking.models.Hotel;
import bitlab.g1.booking.models.HotelManager;
import bitlab.g1.booking.models.RoomType;
import bitlab.g1.booking.repositories.HotelRepository;
import bitlab.g1.booking.repositories.RoomTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;
    private final HotelRepository hotelRepository;
    private final ManagerService managerService;

    private final RoomTypeMapperImpl roomTypeMapper = new RoomTypeMapperImpl();

    public List<RoomTypeDTO> getAllRoomTypes() {
        return roomTypeRepository.findAll().stream().map(roomTypeMapper::toDto).collect(Collectors.toList());
    }

    public List<RoomTypeDTO> getRoomTypesByHotelId(Long hotelId) {
        return roomTypeRepository.findByHotelId(hotelId).stream().map(roomTypeMapper::toDto).collect(Collectors.toList());
    }

    public List<RoomTypeDTO> getRoomTypesByManager(UserDetails managerDetails) {
        HotelManager manager = managerService.findByEmail(managerDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        return this.getRoomTypesByHotelId(manager.getHotel().getId());
    }

    public RoomTypeDTO getNewRoomType(UserDetails userDetails) {
        HotelManager manager = managerService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        RoomTypeDTO roomTypeDTO = new RoomTypeDTO();
        roomTypeDTO.setHotelId(manager.getHotel().getId());
        return roomTypeDTO;
    }

    public RoomTypeDTO getRoomTypeById(Long id) {
        RoomType roomType = roomTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("Room type not found"));
        return roomTypeMapper.toDto(roomType);
    }


    public void createRoomType(RoomTypeDTO roomTypeDTO) {
        RoomType roomType = new RoomType();
        roomType.setName(roomTypeDTO.getName());
        roomType.setDescription(roomTypeDTO.getDescription());
        roomType.setMaxCapacity(roomTypeDTO.getMaxCapacity());

        Hotel hotel = hotelRepository.findById(roomTypeDTO.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        roomType.setHotel(hotel);

        roomTypeRepository.save(roomType);
    }

    public void updateRoomType(RoomTypeDTO roomTypeDTO) {
        RoomType roomType = roomTypeRepository.findById(roomTypeDTO.getId())
                .orElseThrow(() -> new RuntimeException("Room type not found"));
        roomType.setName(roomTypeDTO.getName());
        roomType.setDescription(roomTypeDTO.getDescription());
        roomType.setMaxCapacity(roomTypeDTO.getMaxCapacity());

        Hotel hotel = hotelRepository.findById(roomTypeDTO.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        roomType.setHotel(hotel);

        roomTypeRepository.save(roomType);
    }

    public void deleteRoomType(Long id) {
        roomTypeRepository.deleteById(id);
    }
}
