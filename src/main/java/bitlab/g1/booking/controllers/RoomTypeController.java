package bitlab.g1.booking.controllers;

import bitlab.g1.booking.dto.RoomTypeDTO;
import bitlab.g1.booking.models.HotelManager;
import bitlab.g1.booking.service.ManagerService;
import bitlab.g1.booking.service.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager/room_types")
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @GetMapping("/add")
    public String addRoomTypeForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("roomType", roomTypeService.getNewRoomType(userDetails));
        model.addAttribute("model", "roomtypes");
        return "manager/add-roomtype";
    }

    @PostMapping("/add")
    public String addRoomType(@ModelAttribute RoomTypeDTO roomTypeDTO) {
        roomTypeService.createRoomType(roomTypeDTO);
        return "redirect:/manager/room_types";
    }

    @GetMapping
    public String listRoomTypes(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("roomTypes", roomTypeService.getRoomTypesByManager(userDetails));
        model.addAttribute("model", "roomtypes");
        return "manager/roomtypes";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoomType(@PathVariable Long id) {
        roomTypeService.deleteRoomType(id);
        return "redirect:/manager/room_types";
    }
}
