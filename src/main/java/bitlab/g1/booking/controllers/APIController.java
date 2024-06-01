package bitlab.g1.booking.controllers;

import bitlab.g1.booking.dto.HotelDTO;
import bitlab.g1.booking.dto.RoomDTO;
import bitlab.g1.booking.models.Room;
import bitlab.g1.booking.service.HotelService;
import bitlab.g1.booking.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class APIController {
    private final HotelService hotelService;
    private final RoomService roomService;

    @GetMapping("get-hotels")
    public List<HotelDTO> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("get-hotels/{city}")
    public List<HotelDTO> getAllHotelsByCity(@PathVariable String city) {
        return hotelService.getHotelsByCity(city);
    }

    @GetMapping("/get-rooms/{roomTypeId}")
    public List<RoomDTO> getFreeRoomsByRoomType(@PathVariable Long roomTypeId) {
        return roomService.getFreeRoomsByRoomTypeId(roomTypeId);
    }

    @GetMapping("get-locations")
    public List<String> getLocations(){
        return hotelService.getLocations();
    }

}
