package bitlab.g1.booking.testing;

import bitlab.g1.booking.dto.GuestDTO;
import bitlab.g1.booking.dto.ReservationDTO;
import bitlab.g1.booking.mapper.ReservationMapperImpl;
import bitlab.g1.booking.models.Guest;
import bitlab.g1.booking.models.Reservation;
import bitlab.g1.booking.models.Room;
import bitlab.g1.booking.repositories.ReservationRepository;
import bitlab.g1.booking.service.RegistrationService;
import bitlab.g1.booking.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegistrationServiceTest {

    @InjectMocks
    private RegistrationService registrationService;

    @Mock
    private RoomService roomService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationMapperImpl reservationMapper;

    private GuestDTO guestDTO;
    private ReservationDTO reservationDTO;
    private Reservation reservation;
    private Room room;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        guestDTO = new GuestDTO();
        guestDTO.setId(1L);
        guestDTO.setEmail("guest@example.com");

        reservationDTO = new ReservationDTO();
        reservationDTO.setId(1L);
        reservationDTO.setGuestId(guestDTO.getId());
        reservationDTO.setRoomId(1L);

        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setGuest(new Guest());

        room = new Room();
        room.setId(1L);
        room.setStatus("available");
    }

    @Test
    public void testGetNew() {
        ReservationDTO newReservation = registrationService.getNew(guestDTO);

        assertNotNull(newReservation);
        assertEquals(guestDTO.getId(), newReservation.getGuestId());
    }

    @Test
    public void testSave() {
        when(reservationMapper.toEntity(any(ReservationDTO.class))).thenReturn(reservation);
        when(roomService.getRoomById(anyLong())).thenReturn(room);
        doNothing().when(roomService).setStatusById(anyLong(), anyString());
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        registrationService.save(reservationDTO);

        verify(roomService, times(1)).setStatusById(reservationDTO.getRoomId(), "reserved");
        verify(reservationMapper, times(1)).toEntity(reservationDTO);
        verify(roomService, times(1)).getRoomById(reservationDTO.getRoomId());
        verify(reservationRepository, times(1)).save(reservation);
    }
}