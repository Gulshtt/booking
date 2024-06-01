package bitlab.g1.booking.service;

import bitlab.g1.booking.dto.HotelDTO;
import bitlab.g1.booking.dto.HotelManagerDTO;
import bitlab.g1.booking.mapper.HotelManagerMapperImpl;
import bitlab.g1.booking.mapper.HotelMapperImpl;
import bitlab.g1.booking.models.HotelManager;
import bitlab.g1.booking.repositories.HotelManagerRepository;
import bitlab.g1.booking.repositories.HotelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final HotelManagerRepository hotelManagerRepository;
    private final HotelRepository hotelRepository;
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

    private final HotelManagerMapperImpl managerMapper = new HotelManagerMapperImpl();

    public List<HotelManagerDTO> getManagersByHotelSlug(String hotelSlug) {
        Long hotelId = hotelRepository.findBySlug(hotelSlug)
                .orElseThrow(() -> new RuntimeException("Hotel not found")).getId();
        return hotelManagerRepository.findByHotelId(hotelId).stream().map(managerMapper::toDto).collect(Collectors.toList());
    }

    public List<HotelManagerDTO> getAllManagers(){
        return hotelManagerRepository.findAll().stream().map(managerMapper::toDto).toList();
    }

    public void addManagerToHotel(HotelManagerDTO managerDTO, String hotelSlug) {
        HotelManager hotelManager = managerMapper.toEntity(managerDTO);
        hotelManager.setCreatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())); // Set createdAt field

        Long hotelId = hotelRepository.findBySlug(hotelSlug)
                .orElseThrow(() -> new RuntimeException("Hotel not found")).getId();
        hotelManager.setHotel(hotelRepository.findById(hotelId).orElseThrow(() -> new RuntimeException("Hotel not found")));

        hotelManagerRepository.save(hotelManager);

        inMemoryUserDetailsManager.createUser(
                User.withUsername(managerDTO.getEmail())
                        .password("{noop}" + managerDTO.getPassword()) // "{noop}" indicates no password encoding
                        .roles("MANAGER")
                        .build()
        );
    }

    public HotelManager getHotelManagerById(Long managerId){
        return hotelManagerRepository.findById(managerId).orElseThrow(() -> new RuntimeException("Manager not found"));
    }

    public Optional<HotelManager> findByEmail(String email) {
        return hotelManagerRepository.findByEmail(email);
    }

    public void delete(Long id){
        hotelManagerRepository.deleteById(id);
    }

    @Transactional
    public void updateManager(HotelManagerDTO managerDTO) {
        HotelManagerDTO existingManager = managerMapper.toDto(hotelManagerRepository.findById(managerDTO.getId())
                .orElseThrow(() -> new RuntimeException("Hotel not found")));

        existingManager.setFullName(managerDTO.getFullName());
        existingManager.setEmail(managerDTO.getEmail());
        existingManager.setHotelId(managerDTO.getHotelId());

        hotelManagerRepository.save(managerMapper.toEntity(existingManager));
    }
}
