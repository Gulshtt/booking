package bitlab.g1.booking.testing;

import bitlab.g1.booking.dto.HotelDTO;
import bitlab.g1.booking.dto.HotelManagerDTO;
import bitlab.g1.booking.mapper.HotelManagerMapper;
import bitlab.g1.booking.mapper.HotelMapper;
import bitlab.g1.booking.models.Hotel;
import bitlab.g1.booking.models.HotelManager;
import bitlab.g1.booking.repositories.HotelManagerRepository;
import bitlab.g1.booking.repositories.HotelRepository;
import bitlab.g1.booking.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelManagerRepository hotelManagerRepository;

    @Mock
    private HotelMapper hotelMapper;

    @Mock
    private HotelManagerMapper hotelManagerMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateHotel() {
        HotelDTO hotelDTO = new HotelDTO();
        Hotel hotel = new Hotel();

        when(hotelMapper.toEntity(any(HotelDTO.class))).thenReturn(hotel);
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);
        when(hotelMapper.toDto(any(Hotel.class))).thenReturn(hotelDTO);

        HotelDTO result = adminService.createHotel(hotelDTO);

        assertEquals(hotelDTO, result);
    }

    @Test
    public void testCreateHotelManager() {
        HotelManagerDTO hotelManagerDTO = new HotelManagerDTO();
        HotelManager hotelManager = new HotelManager();

        when(hotelManagerMapper.toEntity(any(HotelManagerDTO.class))).thenReturn(hotelManager);
        when(hotelManagerRepository.save(any(HotelManager.class))).thenReturn(hotelManager);
        when(hotelManagerMapper.toDto(any(HotelManager.class))).thenReturn(hotelManagerDTO);

        HotelManagerDTO result = adminService.createHotelManager(hotelManagerDTO);

        assertEquals(hotelManagerDTO, result);
    }
}
