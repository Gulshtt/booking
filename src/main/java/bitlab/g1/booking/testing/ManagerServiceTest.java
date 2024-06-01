package bitlab.g1.booking.testing;
import bitlab.g1.booking.dto.HotelManagerDTO;
import bitlab.g1.booking.mapper.HotelManagerMapperImpl;
import bitlab.g1.booking.models.Hotel;
import bitlab.g1.booking.models.HotelManager;
import bitlab.g1.booking.repositories.HotelManagerRepository;
import bitlab.g1.booking.repositories.HotelRepository;
import bitlab.g1.booking.service.ManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ManagerServiceTest {

    @InjectMocks
    private ManagerService managerService;

    @Mock
    private HotelManagerRepository hotelManagerRepository;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Mock
    private HotelManagerMapperImpl managerMapper;

    private HotelManager hotelManager;
    private HotelManagerDTO hotelManagerDTO;
    private Hotel hotel;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        hotelManager = new HotelManager();
        hotelManager.setId(1L);
        hotelManager.setFullName("John Doe");
        hotelManager.setEmail("john.doe@example.com");
        hotelManager.setCreatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        hotelManagerDTO = new HotelManagerDTO();
        hotelManagerDTO.setId(1L);
        hotelManagerDTO.setFullName("John Doe");
        hotelManagerDTO.setEmail("john.doe@example.com");
        hotelManagerDTO.setPassword("password");

        hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");
        hotel.setSlug("test-hotel");
    }

    @Test
    public void testGetManagersByHotelSlug() {
        when(hotelRepository.findBySlug(anyString())).thenReturn(Optional.of(hotel));
        when(hotelManagerRepository.findByHotelId(anyLong())).thenReturn(List.of(hotelManager));
        when(managerMapper.toDto(any(HotelManager.class))).thenReturn(hotelManagerDTO);

        List<HotelManagerDTO> result = managerService.getManagersByHotelSlug("test-hotel");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(hotelManagerDTO, result.get(0));
    }

    @Test
    public void testGetAllManagers() {
        when(hotelManagerRepository.findAll()).thenReturn(List.of(hotelManager));
        when(managerMapper.toDto(any(HotelManager.class))).thenReturn(hotelManagerDTO);

        List<HotelManagerDTO> result = managerService.getAllManagers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(hotelManagerDTO, result.get(0));
    }

    @Test
    public void testAddManagerToHotel() {
        when(hotelRepository.findBySlug(anyString())).thenReturn(Optional.of(hotel));
        when(managerMapper.toEntity(any(HotelManagerDTO.class))).thenReturn(hotelManager);
        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(hotel));
        when(hotelManagerRepository.save(any(HotelManager.class))).thenReturn(hotelManager);

        managerService.addManagerToHotel(hotelManagerDTO, "test-hotel");

        verify(hotelManagerRepository, times(1)).save(hotelManager);
        verify(inMemoryUserDetailsManager, times(1)).createUser(any(User.class));
    }

    @Test
    public void testGetHotelManagerById() {
        when(hotelManagerRepository.findById(anyLong())).thenReturn(Optional.of(hotelManager));

        HotelManager result = managerService.getHotelManagerById(1L);

        assertNotNull(result);
        assertEquals(hotelManager, result);
    }

    @Test
    public void testFindByEmail() {
        when(hotelManagerRepository.findByEmail(anyString())).thenReturn(Optional.of(hotelManager));

        Optional<HotelManager> result = managerService.findByEmail("john.doe@example.com");

        assertTrue(result.isPresent());
        assertEquals(hotelManager, result.get());
    }

    @Test
    public void testDelete() {
        doNothing().when(hotelManagerRepository).deleteById(anyLong());

        managerService.delete(1L);

        verify(hotelManagerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateManager() {
        when(hotelManagerRepository.findById(anyLong())).thenReturn(Optional.of(hotelManager));
        when(managerMapper.toDto(any(HotelManager.class))).thenReturn(hotelManagerDTO);
        when(managerMapper.toEntity(any(HotelManagerDTO.class))).thenReturn(hotelManager);

        managerService.updateManager(hotelManagerDTO);

        verify(hotelManagerRepository, times(1)).findById(hotelManagerDTO.getId());
        verify(hotelManagerRepository, times(1)).save(hotelManager);
    }
}