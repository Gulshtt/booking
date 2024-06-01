package bitlab.g1.booking.controllers;

import bitlab.g1.booking.dto.GuestDTO;
import bitlab.g1.booking.dto.HandReservationDTO;
import bitlab.g1.booking.dto.HotelManagerDTO;
import bitlab.g1.booking.dto.ReservationDTO;
import bitlab.g1.booking.models.Guest;
import bitlab.g1.booking.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/booking")
public class ReservationsController {

    private final RegistrationService registrationService;
    private final RoomTypeService roomTypeService;
    private final HotelService hotelService;
    private final GuestService guestService;

    @GetMapping("/hotel/{hotelSlug}")
    public String startReservation(@AuthenticationPrincipal UserDetails guestDetails, Model model, @PathVariable String hotelSlug) {
        GuestDTO guest = guestService.findByEmail(guestDetails.getUsername());

        model.addAttribute("handRegistration", registrationService.getNew(guest));
        model.addAttribute("gusetID", guest.getId());
        model.addAttribute("roomTypes", roomTypeService.getRoomTypesByHotelId(hotelService.getHotelBySlug(hotelSlug).getId()));

        return "hotel-booking";
    }

    @PostMapping("/add")
    public String reservation(@ModelAttribute ReservationDTO reservationDTO){
        registrationService.save(reservationDTO);
        return "reservationComplited";
    }

}
