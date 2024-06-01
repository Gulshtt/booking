package bitlab.g1.booking.controllers;

import bitlab.g1.booking.dto.HandReservationDTO;
import bitlab.g1.booking.dto.HotelManagerDTO;
import bitlab.g1.booking.mapper.HotelManagerMapperImpl;
import bitlab.g1.booking.service.HandReservationService;
import bitlab.g1.booking.service.ManagerService;
import bitlab.g1.booking.service.RegistrationService;
import bitlab.g1.booking.service.RoomTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/manager/reservations")
public class HandReservationController {
//    Logger logger = LoggerFactory.getLogger(HandReservationController.class);

    @Autowired
    private HandReservationService handReservationService;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private RoomTypeService roomTypeService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private HotelManagerMapperImpl managerMapper;

    @GetMapping("/add")
    public String reservationForm(@AuthenticationPrincipal UserDetails managerDetails, Model model){
        HotelManagerDTO hotelManagerDTO = managerMapper.toDto(managerService.findByEmail(managerDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Manager not found")));

        model.addAttribute("handRegistration", handReservationService.getNew(managerDetails));
        model.addAttribute("myManageId", hotelManagerDTO.getId());
        model.addAttribute("roomTypes", roomTypeService.getRoomTypesByManager(managerDetails));
        model.addAttribute("model", "reservations");
        return "manager/reservations";
    }

    @PostMapping
    public String reservation(@AuthenticationPrincipal UserDetails managerDetails, @ModelAttribute HandReservationDTO reservationDTO) {
        HotelManagerDTO hotelManagerDTO = managerMapper.toDto(managerService.findByEmail(managerDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Manager not found")));
        reservationDTO.setManagerId(hotelManagerDTO.getId());
        handReservationService.save(reservationDTO);
        return "redirect:/manager/hotel";
    }

    @GetMapping("/{reservationId}/edit")
    public String reservationEditForm(Model model, @AuthenticationPrincipal UserDetails managerDetails, @PathVariable Long reservationId){
        model.addAttribute("roomTypes", roomTypeService.getRoomTypesByManager(managerDetails));
        model.addAttribute("reservation", handReservationService.getById(reservationId));
        model.addAttribute("model", "reservations");
        return "manager/reservations-edit";
    }

    @PostMapping("/{reservationId}/edit")
    public String reservationEdit(@ModelAttribute HandReservationDTO reservationDTO) {
        if (reservationDTO.getId() == null) {
            throw new IllegalArgumentException("The given id must not be null");
        }
        handReservationService.update(reservationDTO);
        return "redirect:/manager/reservations";
    }

    @GetMapping("/delete/{reservationId}")
    public String deleteHandReservation(@PathVariable Long reservationId) {
        handReservationService.delete(reservationId);
        return "redirect:/manager/reservations";
    }

    @GetMapping
    public String reservationList(@AuthenticationPrincipal UserDetails managerDetails, Model model) {
        ArrayList<HandReservationDTO> r = new ArrayList<>(handReservationService.getReservationsByManager(managerDetails));
        r.addAll(registrationService.getList());
        model.addAttribute("reservations", r);
        model.addAttribute("model", "reservations");
        return "manager/reservation-list";
    }
}
