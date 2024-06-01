package bitlab.g1.booking.controllers;

import bitlab.g1.booking.dto.HotelDTO;
import bitlab.g1.booking.dto.HotelManagerDTO;
import bitlab.g1.booking.service.HotelService;
import bitlab.g1.booking.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private final HotelService hotelService;

    @Autowired
    private final ManagerService managerService;

    @GetMapping("hotels")
    public String getAllHotels(Model model) {
        model.addAttribute("hotels", hotelService.getAllHotels());
        model.addAttribute("model", "hotels");
        return "admin/hotels";
    }

    @GetMapping("hotel/{hotelSlug}/view")
    public String viewHotel(Model model, @PathVariable String hotelSlug) {
        model.addAttribute("hotel", hotelService.getHotelBySlug(hotelSlug));
        model.addAttribute("model", "hotels");
        return "admin/single-hotel";
    }

    @GetMapping("hotels/{hotelSlug}/manager-list")
    public String getManagerList(Model model, @PathVariable String hotelSlug) {
        model.addAttribute("managers", managerService.getManagersByHotelSlug(hotelSlug));
        model.addAttribute("model", "managers");
        return "admin/manager-list";
    }

    @GetMapping("managers")
    public String getFullManagersList(Model model) {
        model.addAttribute("managers", managerService.getAllManagers());
        model.addAttribute("model", "managers");
        return "admin/manager-list";
    }

    @GetMapping("hotels/{hotelSlug}/add-manager")
    public String addManagerForm(Model model, @PathVariable String hotelSlug) {
        model.addAttribute("manager", new HotelManagerDTO());
        model.addAttribute("hotelSlug", hotelSlug);
        model.addAttribute("model", "managers");
        return "admin/add-manager";
    }

    @PostMapping("hotel/{hotelSlug}/add-manager")
    public String addManager(@ModelAttribute HotelManagerDTO managerDTO, @PathVariable String hotelSlug) {
        managerService.addManagerToHotel(managerDTO, hotelSlug);
        return "redirect:/admin/hotels/" + hotelSlug + "/manager-list";
    }

    @GetMapping("hotels/{hotelSlug}/manager/{managerId}/delete")
    public String deleteManager(@PathVariable Long managerId, @PathVariable String hotelSlug) {
        managerService.delete(managerId);
        return "redirect:/admin/hotels/" + hotelSlug + "/manager-list";
    }

    @GetMapping("/hotel/{hotelSlug}/edit")
    public String editHotel(Model model, @PathVariable String hotelSlug) {
        model.addAttribute("hotel", hotelService.getHotelBySlug(hotelSlug));
        model.addAttribute("model", "hotels");
        return "admin/edit-hotel";
    }

    @PostMapping("/hotel/{hotelSlug}/edit")
    public String updateHotel(@ModelAttribute HotelDTO hotelDTO, @PathVariable String hotelSlug) {
        hotelService.updateHotel(hotelDTO);
        return "redirect:/admin/hotels";
    }

    @GetMapping("/hotel/{hotelId}/delete")
    public String deleteHotel(@PathVariable Long hotelId) {
        hotelService.deleteHotel(hotelId);
        return "redirect:/admin/hotels";
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("model", "dashboard");
        model.addAttribute("hotelsCount", hotelService.getAllHotels().toArray().length);
        model.addAttribute("managerCount", managerService.getAllManagers().toArray().length);
        return "admin/dashboard";
    }

    @GetMapping("/hotels/add")
    public String addHotelForm(Model model) {
        model.addAttribute("hotel", new HotelDTO());
        model.addAttribute("model", "hotels");
        return "admin/add-hotel";
    }

    @PostMapping("/hotels/add")
    public String addHotel(@ModelAttribute HotelDTO hotelDTO) {
        hotelService.createHotel(hotelDTO);
        return "redirect:/admin/hotels";
    }
}
