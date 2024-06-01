package bitlab.g1.booking.controllers;

import bitlab.g1.booking.dto.RoomDTO;
import bitlab.g1.booking.service.RoomService;
import bitlab.g1.booking.service.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager/rooms")
@RequiredArgsConstructor
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomTypeService roomTypeService;

    @GetMapping
    public String getRoomsByHotel(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("rooms", roomService.getRoomsByManager(userDetails));
        model.addAttribute("model", "rooms");
        return "manager/rooms";
    }

    @GetMapping("/add")
    public String addRoomForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("room", new RoomDTO());
        model.addAttribute("roomTypes", roomTypeService.getRoomTypesByManager(userDetails));
        model.addAttribute("model", "rooms");
        return "manager/add-room";
    }

    @PostMapping("/add")
    public String addRoom(@ModelAttribute RoomDTO roomDTO) {
        roomService.saveRoom(roomDTO);
        return "redirect:/manager/rooms";
    }

    @GetMapping("/{roomId}/edit")
    public String editRoomForm(@AuthenticationPrincipal UserDetails userDetails, Model model, @PathVariable Long roomId) {
        model.addAttribute("room", roomService.getRoomDTOById(roomId));
        model.addAttribute("roomTypes", roomTypeService.getRoomTypesByManager(userDetails));
        model.addAttribute("model", "rooms");
        return "manager/edit-room";
    }

    @PostMapping("/{roomId}/edit")
    public String editRoom(@ModelAttribute RoomDTO roomDTO) {
        roomService.saveRoom(roomDTO);
        return "redirect:/manager/rooms";
    }

    @GetMapping("/{roomId}/delete")
    public String deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return "redirect:/manager/rooms";
    }
}
