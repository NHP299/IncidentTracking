package  com.tracking.mapper;

import  com.tracking.domain.Role;
import  com.tracking.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO toDTO(Role role);

    Role toEntity(RoleDTO dto);
    List<RoleDTO> toDTOList(List<Role> roles);
}
