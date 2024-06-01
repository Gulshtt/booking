package bitlab.g1.booking.testing;

import bitlab.g1.booking.dto.RoomTypeDTO;
import bitlab.g1.booking.mapper.RoomTypeMapperImpl;
import bitlab.g1.booking.models.Hotel;
import bitlab.g1.booking.models.HotelManager;
import bitlab.g1.booking.models.RoomType;
import bitlab.g1.booking.repositories.HotelRepository;
import bitlab.g1.booking.repositories.RoomTypeRepository;
import bitlab.g1.booking.service.ManagerService;
import bitlab.g1.booking.service.RoomTypeService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RoomTypeServiceTest {

    @InjectMocks
    private RoomTypeService roomTypeService;

    @Mock
    private RoomTypeRepository roomTypeRepository;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private ManagerService managerService;

    @Mock
    private RoomTypeMapperImpl roomTypeMapper;

    private HotelManager manager;
    private RoomType roomType;
    private RoomTypeDTO roomTypeDTO;
    private UserDetails userDetails;
    private Hotel hotel;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        hotel = new Hotel();
        hotel.setId(1L);

        roomType = new RoomType();
        roomType.setId(1L);
        roomType.setHotel(hotel);

        roomTypeDTO = new RoomTypeDTO();
        roomTypeDTO.setId(1L);
        roomTypeDTO.setHotelId(1L);

        manager = new HotelManager();
        manager.setHotel(hotel);

        userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("manager@example.com");
    }

    @Test
    public void testGetAllRoomTypes() {
        List<RoomType> roomTypes = new ArrayList<>();
        roomTypes.add(roomType);

        when(roomTypeRepository.findAll()).thenReturn(roomTypes);
        when(roomTypeMapper.toDto(any(RoomType.class))).thenReturn(roomTypeDTO);

        List<RoomTypeDTO> result = roomTypeService.getAllRoomTypes();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(roomTypeDTO, result.get(0));
    }

    @Test
    public void testGetRoomTypesByHotelId() {
        List<RoomType> roomTypes = new ArrayList<>();
        roomTypes.add(roomType);

        when(roomTypeRepository.findByHotelId(anyLong())).thenReturn(roomTypes);
        when(roomTypeMapper.toDto(any(RoomType.class))).thenReturn(roomTypeDTO);

        List<RoomTypeDTO> result = roomTypeService.getRoomTypesByHotelId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(roomTypeDTO, result.get(0));
    }

    @Test
    public void testGetRoomTypesByManager() {
        when(managerService.findByEmail(anyString())).thenReturn(Optional.of(manager));
        when(roomTypeRepository.findByHotelId(anyLong())).thenReturn(List.of(roomType));
        when(roomTypeMapper.toDto(any(RoomType.class))).thenReturn(roomTypeDTO);

        List<RoomTypeDTO> result = roomTypeService.getRoomTypesByManager(userDetails);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(roomTypeDTO, result.get(0));
    }

    @Test
    public void testGetNewRoomType() {
        when(managerService.findByEmail(anyString())).thenReturn(Optional.of(manager));

        RoomTypeDTO result = roomTypeService.getNewRoomType(userDetails);

        assertNotNull(result);
        assertEquals(manager.getHotel().getId(), result.getHotelId());
    }

    @Test
    public void testGetRoomTypeById() {
        when(roomTypeRepository.findById(anyLong())).thenReturn(Optional.of(roomType));
        when(roomTypeMapper.toDto(any(RoomType.class))).thenReturn(roomTypeDTO);

        RoomTypeDTO result = roomTypeService.getRoomTypeById(1L);

        assertNotNull(result);
        assertEquals(roomTypeDTO, result);
    }

    @Test
    public void testCreateRoomType() {
        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(hotel));

        roomTypeService.createRoomType(roomTypeDTO);

        verify(roomTypeRepository, times(1)).save(any(RoomType.class));
    }

    @Test
    public void testUpdateRoomType() {
        when(roomTypeRepository.findById(anyLong())).thenReturn(Optional.of(roomType));
        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(hotel));

        roomTypeService.updateRoomType(roomTypeDTO);

        verify(roomTypeRepository, times(1)).save(any(RoomType.class));
    }

    @Test
    public void testDeleteRoomType() {
        roomTypeService.deleteRoomType(1L);

        verify(roomTypeRepository, times(1)).deleteById(1L);
    }
}