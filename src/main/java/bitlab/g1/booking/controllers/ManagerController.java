package bitlab.g1.booking.controllers;

import bitlab.g1.booking.dto.HotelDTO;
import bitlab.g1.booking.dto.RoomTypeDTO;
import bitlab.g1.booking.models.HotelManager;
import bitlab.g1.booking.service.HotelService;
import bitlab.g1.booking.service.ManagerService;
import bitlab.g1.booking.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private ManagerService managerService;

    @GetMapping("/dashboard")
    public String viewHotel(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        HotelManager manager = managerService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Manager not found"));
        HotelDTO hotel = hotelService.getHotelById(manager.getHotel().getId());
        model.addAttribute("hotel", hotel);
        model.addAttribute("model", "dashboard");
        return "manager/hotel";
    }
}
