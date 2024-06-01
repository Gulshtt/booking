package bitlab.g1.booking.service;

import bitlab.g1.booking.dto.RoomDTO;
import bitlab.g1.booking.mapper.RoomMapperImpl;
import bitlab.g1.booking.models.HotelManager;
import bitlab.g1.booking.models.Room;
import bitlab.g1.booking.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final ManagerService managerService;
    private final RoomMapperImpl roomMapper = new RoomMapperImpl();

    public List<RoomDTO> getRoomsByHotelId(Long hotelId) {
        return roomRepository.findAll().stream()
                .filter(room -> room.getRoomType().getHotel().getId().equals(hotelId))
                .map(roomMapper::toDto)
                .toList();
    }

    public List<RoomDTO> getRoomsByManager(UserDetails managerDetails) {
        HotelManager manager = managerService.findByEmail(managerDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        return this.getRoomsByHotelId(manager.getHotel().getId());
    }

    public RoomDTO getRoomDTOById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return roomMapper.toDto(room);
    }
    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public void saveRoom(RoomDTO roomDTO) {
        roomRepository.save(roomMapper.toEntity(roomDTO));
    }

    public void setStatusById(Long roomId, String status){
        Room room = this.getRoomById(roomId);
        room.setStatus(status);
        roomRepository.save(room);
    }

    public List<RoomDTO> getRoomsByRoomTypeId(Long roomTypeId) {
        return roomRepository.findByRoomType_Id(roomTypeId).stream().map(roomMapper::toDto).toList();
    }

    public List<RoomDTO> getFreeRoomsByRoomTypeId(Long roomTypeId) {
        List<RoomDTO> res = getRoomsByRoomTypeId(roomTypeId);
        res = res.stream().filter(roomDTO -> roomDTO.getStatus().equals("active")).toList();
        return res;
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
