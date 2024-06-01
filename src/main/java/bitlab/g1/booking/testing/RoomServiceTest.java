package bitlab.g1.booking.testing;

import bitlab.g1.booking.dto.RoomDTO;
import bitlab.g1.booking.mapper.RoomMapperImpl;
import bitlab.g1.booking.models.Hotel;
import bitlab.g1.booking.models.HotelManager;
import bitlab.g1.booking.models.Room;
import bitlab.g1.booking.models.RoomType;
import bitlab.g1.booking.repositories.RoomRepository;
import bitlab.g1.booking.service.ManagerService;
import bitlab.g1.booking.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ManagerService managerService;

    @Mock
    private RoomMapperImpl roomMapper;

    private HotelManager manager;
    private Room room;
    private RoomDTO roomDTO;
    private UserDetails userDetails;
    private Hotel hotel;
    private RoomType roomType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        hotel = new Hotel();
        hotel.setId(1L);

        roomType = new RoomType();
        roomType.setHotel(hotel);

        room = new Room();
        room.setId(1L);
        room.setRoomType(roomType);

        roomDTO = new RoomDTO();
        roomDTO.setId(1L);

        manager = new HotelManager();
        manager.setHotel(hotel);

        userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("manager@example.com");
    }

    @Test
    public void testGetRoomsByHotelId() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(room);

        when(roomRepository.findAll()).thenReturn(rooms);
        when(roomMapper.toDto(any(Room.class))).thenReturn(roomDTO);

        List<RoomDTO> result = roomService.getRoomsByHotelId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(roomDTO, result.get(0));
    }

    @Test
    public void testGetRoomsByManager() {
        when(managerService.findByEmail(anyString())).thenReturn(Optional.of(manager));
        when(roomRepository.findAll()).thenReturn(List.of(room));
        when(roomMapper.toDto(any(Room.class))).thenReturn(roomDTO);

        List<RoomDTO> result = roomService.getRoomsByManager(userDetails);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(roomDTO, result.get(0));
    }

    @Test
    public void testGetRoomDTOById() {
        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room));
        when(roomMapper.toDto(any(Room.class))).thenReturn(roomDTO);

        RoomDTO result = roomService.getRoomDTOById(1L);

        assertNotNull(result);
        assertEquals(roomDTO, result);
    }

    @Test
    public void testGetRoomById() {
        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room));

        Room result = roomService.getRoomById(1L);

        assertNotNull(result);
        assertEquals(room, result);
    }

    @Test
    public void testSaveRoom() {
        when(roomMapper.toEntity(any(RoomDTO.class))).thenReturn(room);

        roomService.saveRoom(roomDTO);

        verify(roomRepository, times(1)).save(room);
    }

    @Test
    public void testSetStatusById() {
        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(room));

        roomService.setStatusById(1L, "reserved");

        assertEquals("reserved", room.getStatus());
        verify(roomRepository, times(1)).save(room);
    }

    @Test
    public void testGetRoomsByRoomTypeId() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(room);

        when(roomRepository.findByRoomType_Id(anyLong())).thenReturn(rooms);
        when(roomMapper.toDto(any(Room.class))).thenReturn(roomDTO);

        List<RoomDTO> result = roomService.getRoomsByRoomTypeId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(roomDTO, result.get(0));
    }

    @Test
    public void testGetFreeRoomsByRoomTypeId() {
        room.setStatus("active");
        List<Room> rooms = new ArrayList<>();
        rooms.add(room);

        when(roomRepository.findByRoomType_Id(anyLong())).thenReturn(rooms);
        when(roomMapper.toDto(any(Room.class))).thenReturn(roomDTO);

        List<RoomDTO> result = roomService.getFreeRoomsByRoomTypeId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(roomDTO, result.get(0));
    }

    @Test
    public void testDeleteRoom() {
        roomService.deleteRoom(1L);

        verify(roomRepository, times(1)).deleteById(1L);
    }
}