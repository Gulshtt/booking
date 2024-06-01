package bitlab.g1.booking.utls;

import bitlab.g1.booking.models.Admin;
import bitlab.g1.booking.models.Guest;
import bitlab.g1.booking.models.HotelManager;
import bitlab.g1.booking.repositories.AdminRepository;
import bitlab.g1.booking.repositories.GuestRepository;
import bitlab.g1.booking.repositories.HotelManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private HotelManagerRepository hotelManagerRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/register" ,"/css/**", "/lib/**", "/img/**", "/js/**", "/signup", "/api/**", "/booking/**", "/hotel/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/manager/**").hasRole("MANAGER")
                        .requestMatchers("/book/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .successHandler(customAuthenticationSuccessHandler())
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    public CustomAuthenticationSuccessHandle customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandle();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        List<Admin> admins = adminRepository.findAll();
        List<Guest> guests = guestRepository.findAll();
        List<HotelManager> managers = hotelManagerRepository.findAll();

        List<UserDetails> adminDetails = admins.stream()
                .map(admin -> User.withUsername(admin.getEmail())
                        .password("{noop}" + admin.getPassword()) // "{noop}" is used to indicate no password encoding
                        .roles("ADMIN")
                        .build())
                .toList();

        List<UserDetails> guestDetails = guests.stream()
                .map(guest -> User.withUsername(guest.getEmail())
                        .password("{noop}" + guest.getPassword()) // "{noop}" is used to indicate no password encoding
                        .roles("USER")  // Role can be adjusted if needed.
                        .build())
                .toList();

        List<UserDetails> managersDetails = managers.stream()
                .map(manager -> User.withUsername(manager.getEmail())
                        .password("{noop}" + manager.getPassword()) // "{noop}" is used to indicate no password encoding
                        .roles("MANAGER")  // Role can be adjusted if needed.
                        .build())
                .toList();

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        adminDetails.forEach(manager::createUser);
        guestDetails.forEach(manager::createUser);
        managersDetails.forEach(manager::createUser);
        return manager;
    }
}
