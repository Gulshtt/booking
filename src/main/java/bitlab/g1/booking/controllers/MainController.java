package bitlab.g1.booking.controllers;

import bitlab.g1.booking.models.Guest;
import bitlab.g1.booking.service.GuestService;
import bitlab.g1.booking.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final GuestService guestService;
    private final HotelService hotelService;

    @GetMapping(value = "/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping(value = "/hello")
    public String hello(Model model) {
        return "hello";
    }

    @GetMapping(value = "/signup")
    public String singUpForm(Model model) {
        model.addAttribute("guest", new Guest());
        return "signup";
    }

    @PostMapping(value = "/signup")
    public String singUp(@ModelAttribute Guest guest, Model model) {
        guestService.save(guest);
        return "redirect:/";
    }

    @GetMapping(value = "/booking")
    public String booking(Model model) {
        model.addAttribute("locations", hotelService.getLocations());
        return "booking";
    }

    @GetMapping(value = "/booking/{location}")
    public String bookingLocation(Model model, @PathVariable String location) {
        model.addAttribute("hotels", hotelService.getHotelsByCity(location));
        return "booking-locations";
    }

    @GetMapping(value = "/hotel/{hotelSlug}")
    public String getHotel(Model model, @PathVariable String hotelSlug) {
        model.addAttribute("hotel", hotelService.getHotelBySlug(hotelSlug));
        return "hotel";
    }
}