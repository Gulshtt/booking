package bitlab.g1.booking.testing;

import bitlab.g1.booking.dto.HotelDTO;
import bitlab.g1.booking.mapper.HotelMapperImpl;
import bitlab.g1.booking.models.Hotel;
import bitlab.g1.booking.repositories.HotelRepository;
import bitlab.g1.booking.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class HotelServiceTest {

    @InjectMocks
    private HotelService hotelService;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapperImpl hotelMapper;

    private Hotel hotel;
    private HotelDTO hotelDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");
        hotel.setAddress("Test Address");
        hotel.setDescription("Test Description");
        hotel.setRating(5.0);
        hotel.setSlug("test-hotel");
        hotel.setImage("test-image.jpg");

        hotelDTO = new HotelDTO();
        hotelDTO.setId(1L);
        hotelDTO.setName("Test Hotel");
        hotelDTO.setAddress("Test Address");
        hotelDTO.setDescription("Test Description");
        hotelDTO.setRating(5.0);
        hotelDTO.setSlug("test-hotel");
        hotelDTO.setImage("test-image.jpg");
    }

    @Test
    public void testGetAllHotels() {
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel);
        when(hotelRepository.findAll()).thenReturn(hotels);
        when(hotelMapper.toDto(any(Hotel.class))).thenReturn(hotelDTO);

        List<HotelDTO> result = hotelService.getAllHotels();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(hotelDTO, result.get(0));
    }

    @Test
    public void testGetHotelsByCity() {
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel);
        when(hotelRepository.findByAddress(anyString())).thenReturn(hotels);
        when(hotelMapper.toDto(any(Hotel.class))).thenReturn(hotelDTO);

        List<HotelDTO> result = hotelService.getHotelsByCity("Test Address");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(hotelDTO, result.get(0));
    }

    @Test
    public void testGetHotelBySlug() {
        when(hotelRepository.findBySlug(anyString())).thenReturn(Optional.of(hotel));
        when(hotelMapper.toDto(any(Hotel.class))).thenReturn(hotelDTO);

        HotelDTO result = hotelService.getHotelBySlug("test-hotel");

        assertNotNull(result);
        assertEquals(hotelDTO, result);
    }

    @Test
    public void testCreateHotel() {
        when(hotelMapper.toEntity(any(HotelDTO.class))).thenReturn(hotel);
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        hotelService.createHotel(hotelDTO);

        verify(hotelRepository, times(1)).save(hotel);
    }

    @Test
    public void testUpdateHotel() {
        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(hotel));
        when(hotelMapper.toDto(any(Hotel.class))).thenReturn(hotelDTO);
        when(hotelMapper.toEntity(any(HotelDTO.class))).thenReturn(hotel);
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        hotelService.updateHotel(hotelDTO);

        verify(hotelRepository, times(1)).findById(hotelDTO.getId());
        verify(hotelRepository, times(1)).save(hotel);
    }

    @Test
    public void testGetLocations() {
        List<String> locations = new ArrayList<>();
        locations.add("Test Address");
        when(hotelRepository.findDistinctAddresses()).thenReturn(locations);

        List<String> result = hotelService.getLocations();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Address", result.get(0));
    }

    @Test
    public void testGetHotelById() {
        when(hotelRepository.findById(anyLong())).thenReturn(Optional.of(hotel));
        when(hotelMapper.toDto(any(Hotel.class))).thenReturn(hotelDTO);

        HotelDTO result = hotelService.getHotelById(1L);

        assertNotNull(result);
        assertEquals(hotelDTO, result);
    }

    @Test
    public void testDeleteHotel() {
        doNothing().when(hotelRepository).deleteById(anyLong());

        hotelService.deleteHotel(1L);

        verify(hotelRepository, times(1)).deleteById(1L);
    }
}
