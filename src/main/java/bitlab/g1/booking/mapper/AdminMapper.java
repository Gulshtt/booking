package bitlab.g1.booking.mapper;

import bitlab.g1.booking.dto.AdminDTO;
import bitlab.g1.booking.models.Admin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    AdminDTO toDto(Admin admin);
    Admin toEntity(AdminDTO adminDTO);
}
