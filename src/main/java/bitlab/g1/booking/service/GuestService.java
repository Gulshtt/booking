package bitlab.g1.booking.service;

import bitlab.g1.booking.dto.GuestDTO;
import bitlab.g1.booking.mapper.GuestMapperImpl;
import bitlab.g1.booking.models.Guest;
import bitlab.g1.booking.repositories.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestService {
    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
    private final GuestRepository guestRepository;
    private final GuestMapperImpl guestMapper;

    public GuestDTO getNew(){
        return new GuestDTO();
    }

    public GuestDTO findByEmail(String email) {
        return guestMapper.toDto(guestRepository.findByEmail(email));
    }

    public void saveDTO(GuestDTO guestDTO){
        this.save(guestMapper.toEntity(guestDTO));
    }
    public void save(Guest guest){
        inMemoryUserDetailsManager.createUser(
                User.withUsername(guest.getEmail())
                        .password("{noop}" + guest.getPassword())
                        .roles("GUEST")
                        .build()
        );
        guestRepository.save(guest);
    }

}
