package bitlab.g1.booking.service;

import bitlab.g1.booking.controllers.HandReservationController;
import bitlab.g1.booking.dto.HandReservationDTO;
import bitlab.g1.booking.mapper.HandReservationMapperImpl;
import bitlab.g1.booking.models.HandReservation;
import bitlab.g1.booking.models.HotelManager;
import bitlab.g1.booking.models.Room;
import bitlab.g1.booking.models.RoomType;
import bitlab.g1.booking.repositories.HandReservationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HandReservationService {
    private final HandReservationRepository handReservationRepository;
    private final ManagerService managerService;
    private final RoomService roomService;
    private final HandReservationMapperImpl handReservationMapper;

    public HandReservationDTO getNew(UserDetails managerDetails){
        HotelManager manager = managerService.findByEmail(managerDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        HandReservationDTO res = new HandReservationDTO();
        res.setManagerId(manager.getId());
        return res;
    }

    public void save(HandReservationDTO reservationDTO) {
        roomService.setStatusById(reservationDTO.getRoomId(), "reserved");

        HandReservation handReservation = handReservationMapper.toEntity(reservationDTO);
        handReservation.setRoom(roomService.getRoomById(reservationDTO.getRoomId()));
        handReservation.setManager(managerService.getHotelManagerById(reservationDTO.getManagerId()));
        handReservationRepository.save(handReservation);
    }

    public void update(HandReservationDTO reservationDTO) {
        if (reservationDTO.getId() == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }
        HandReservation reservation = handReservationRepository.findById(reservationDTO.getId())
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.getRoom().setStatus("active");
        roomService.getRoomById(reservationDTO.getRoomId()).setStatus("reserved");

        reservation.setRoom(roomService.getRoomById(reservationDTO.getRoomId()));
        reservation.setStart_date(reservationDTO.getStart_date());
        reservation.setEnd_date(reservationDTO.getEnd_date());
        reservation.setGuestFullName(reservationDTO.getGuestFullName());
        reservation.setGuestEmail(reservationDTO.getGuestEmail());
        reservation.setGuestPhoneNumber(reservationDTO.getGuestPhoneNumber());

        handReservationRepository.save(reservation);
    }


    public HandReservationDTO getById(Long id) {
        return handReservationMapper.toDto(handReservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Manager not found")));
    }

    public List<HandReservationDTO> getReservationsByManager(UserDetails managerDetails) {
        HotelManager manager = managerService.findByEmail(managerDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        return handReservationRepository.getHandReservationByManagerId(manager.getId()).stream().map(reservation -> {
            HandReservationDTO dto = handReservationMapper.toDto(reservation);
            Room room = reservation.getRoom();
            RoomType roomType = reservation.getRoom().getRoomType();
            if (room != null) {
                dto.setRoomNumber(room.getNumber());
            }
            if (roomType != null) {
                dto.setRoomTypeName(roomType.getName());
            }
            return dto;
        }).toList();
    }

    public void delete(Long id){
        HandReservation handReservation = handReservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Hotel not found with slug: "));
        handReservation.getRoom().setStatus("active");
        handReservationRepository.deleteById(id);
    }
}
