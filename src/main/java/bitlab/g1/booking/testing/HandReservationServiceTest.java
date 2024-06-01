package bitlab.g1.booking.testing;

import bitlab.g1.booking.dto.HandReservationDTO;
import bitlab.g1.booking.mapper.HandReservationMapperImpl;
import bitlab.g1.booking.models.HandReservation;
import bitlab.g1.booking.models.HotelManager;
import bitlab.g1.booking.models.Room;
import bitlab.g1.booking.models.RoomType;
import bitlab.g1.booking.repositories.HandReservationRepository;
import bitlab.g1.booking.service.HandReservationService;
import bitlab.g1.booking.service.ManagerService;
import bitlab.g1.booking.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class HandReservationServiceTest {

    @InjectMocks
    private HandReservationService handReservationService;

    @Mock
    private HandReservationRepository handReservationRepository;

    @Mock
    private ManagerService managerService;

    @Mock
    private RoomService roomService;

    @Mock
    private HandReservationMapperImpl handReservationMapper;

    @Mock
    private UserDetails managerDetails;

    private HandReservationDTO reservationDTO;
    private HandReservation reservation;
    private HotelManager manager;
    private Room room;
    private RoomType roomType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        reservationDTO = new HandReservationDTO();
        reservationDTO.setId(1L);
        reservationDTO.setManagerId(1L);
        reservationDTO.setRoomId(1L);
        reservationDTO.setGuestFullName("Guest");
        reservationDTO.setGuestEmail("guest@example.com");
        reservationDTO.setGuestPhoneNumber("1234567890");
        reservationDTO.setStart_date(Calendar.getInstance().getTime());
        reservationDTO.setEnd_date(Calendar.getInstance().getTime());

        manager = new HotelManager();
        manager.setId(1L);
        manager.setEmail("manager@example.com");

        roomType = new RoomType();
        roomType.setId(1L);
        roomType.setName("Deluxe");

        room = new Room();
        room.setId(1L);
        room.setStatus("active");
        room.setRoomType(roomType);

        reservation = new HandReservation();
        reservation.setId(1L);
        reservation.setRoom(room);
        reservation.setManager(manager);
    }

    @Test
    public void testGetNew() {
        when(managerDetails.getUsername()).thenReturn("manager@example.com");
        when(managerService.findByEmail(anyString())).thenReturn(Optional.of(manager));

        HandReservationDTO result = handReservationService.getNew(managerDetails);

        assertNotNull(result);
        assertEquals(manager.getId(), result.getManagerId());
    }

    @Test
    public void testSave() {
        when(roomService.getRoomById(anyLong())).thenReturn(room);
        when(managerService.getHotelManagerById(anyLong())).thenReturn(manager);
        when(handReservationMapper.toEntity(any(HandReservationDTO.class))).thenReturn(reservation);

        handReservationService.save(reservationDTO);

        verify(roomService, times(1)).setStatusById(reservationDTO.getRoomId(), "reserved");
        verify(handReservationRepository, times(1)).save(reservation);
    }

    @Test
    public void testUpdate() {
        when(handReservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));
        when(roomService.getRoomById(anyLong())).thenReturn(room);

        handReservationService.update(reservationDTO);

        verify(handReservationRepository, times(1)).findById(reservationDTO.getId());
        verify(handReservationRepository, times(1)).save(reservation);
        assertEquals("reserved", reservation.getRoom().getStatus());
    }

    @Test
    public void testGetById() {
        when(handReservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));
        when(handReservationMapper.toDto(any(HandReservation.class))).thenReturn(reservationDTO);

        HandReservationDTO result = handReservationService.getById(1L);

        assertNotNull(result);
        assertEquals(reservationDTO, result);
    }

    @Test
    public void testGetReservationsByManager() {
        List<HandReservation> reservations = new ArrayList<>();
        reservations.add(reservation);

        when(managerDetails.getUsername()).thenReturn("manager@example.com");
        when(managerService.findByEmail(anyString())).thenReturn(Optional.of(manager));
        when(handReservationRepository.getHandReservationByManagerId(anyLong())).thenReturn(reservations);
        when(handReservationMapper.toDto(any(HandReservation.class))).thenReturn(reservationDTO);

        List<HandReservationDTO> result = handReservationService.getReservationsByManager(managerDetails);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(reservationDTO, result.get(0));
    }

    @Test
    public void testDelete() {
        when(handReservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation));

        handReservationService.delete(1L);

        verify(handReservationRepository, times(1)).findById(1L);
        verify(handReservationRepository, times(1)).deleteById(1L);
        assertEquals("active", reservation.getRoom().getStatus());
    }
}
