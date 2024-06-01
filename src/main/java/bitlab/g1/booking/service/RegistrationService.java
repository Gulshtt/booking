package bitlab.g1.booking.service;

import bitlab.g1.booking.dto.GuestDTO;
import bitlab.g1.booking.dto.HandReservationDTO;
import bitlab.g1.booking.dto.ReservationDTO;
import bitlab.g1.booking.mapper.HandReservationMapperImpl;
import bitlab.g1.booking.mapper.ReservationMapperImpl;
import bitlab.g1.booking.models.HandReservation;
import bitlab.g1.booking.models.HotelManager;
import bitlab.g1.booking.models.Reservation;
import bitlab.g1.booking.repositories.GuestRepository;
import bitlab.g1.booking.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final RoomService roomService;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationMapperImpl reservationMapper;
    public ReservationDTO getNew(GuestDTO guest){
        ReservationDTO res = new ReservationDTO();
        res.setGuestId(guest.getId());
        return res;
    }

    public void save(ReservationDTO reservationDTO) {
        roomService.setStatusById(reservationDTO.getRoomId(), "reserved");

        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        reservation.setIsActive(true);
        reservation.setRoom(roomService.getRoomById(reservationDTO.getRoomId()));
        reservation.setGuest(guestRepository.findById(reservationDTO.getGuestId()).orElseThrow(() -> new RuntimeException("Room not found")));
        reservationRepository.save(reservation);
    }

    private HandReservationDTO reservationToHandReservation(Reservation reservation){
        HandReservationDTO res = new HandReservationDTO();
        res.setManagerId(1L);
        res.setRoomId(reservation.getRoom().getId());
        res.setStart_date(reservation.getStart_date());
        res.setEnd_date(reservation.getEnd_date());
        res.setGuestEmail(reservation.getGuest().getEmail());
        res.setGuestFullName(reservation.getGuest().getFullName());
        res.setGuestPhoneNumber(reservation.getGuest().getPhoneNumber());
        res.setIsActive(reservation.getIsActive());
        res.setRoomNumber(reservation.getRoom().getNumber());
        res.setRoomTypeName(reservation.getRoom().getRoomType().getName());

        return res;
    }

    public List<HandReservationDTO> getList(){
        return reservationRepository.findAll().stream().map(this::reservationToHandReservation).toList();
    }
}
