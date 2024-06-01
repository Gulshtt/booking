package bitlab.g1.booking.testing;

import bitlab.g1.booking.dto.GuestDTO;
import bitlab.g1.booking.mapper.GuestMapperImpl;
import bitlab.g1.booking.models.Guest;
import bitlab.g1.booking.repositories.GuestRepository;
import bitlab.g1.booking.service.GuestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GuestServiceTest {

    @InjectMocks
    private GuestService guestService;

    @Mock
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private GuestMapperImpl guestMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetNew() {
        GuestDTO result = guestService.getNew();
        assertNotNull(result);
    }

    @Test
    public void testFindByEmail() {
        String email = "test@example.com";
        Guest guest = new Guest();
        GuestDTO guestDTO = new GuestDTO();

        when(guestRepository.findByEmail(email)).thenReturn(guest);
        when(guestMapper.toDto(any(Guest.class))).thenReturn(guestDTO);

        GuestDTO result = guestService.findByEmail(email);

        assertEquals(guestDTO, result);
        verify(guestRepository, times(1)).findByEmail(email);
        verify(guestMapper, times(1)).toDto(guest);
    }

    @Test
    public void testSaveDTO() {
        GuestDTO guestDTO = new GuestDTO();
        Guest guest = new Guest();

        when(guestMapper.toEntity(any(GuestDTO.class))).thenReturn(guest);

        guestService.saveDTO(guestDTO);

        verify(guestMapper, times(1)).toEntity(guestDTO);
        verify(guestRepository, times(1)).save(guest);
        verify(inMemoryUserDetailsManager, times(1)).createUser(any(User.class));
    }

    @Test
    public void testSave() {
        Guest guest = new Guest();
        guest.setEmail("test@example.com");
        guest.setPassword("password");

        guestService.save(guest);

        verify(inMemoryUserDetailsManager, times(1)).createUser(any(User.class));
        verify(guestRepository, times(1)).save(guest);
    }
}