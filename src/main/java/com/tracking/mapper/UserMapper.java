package com.tracking.mapper;

import  com.tracking.domain.User;
import  com.tracking.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Entity -> DTO
    @Mapping(source = "department.departmentId", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(source = "role.roleId", target = "roleId")
    @Mapping(source = "role.roleName", target = "roleName")
    UserDTO toDTO(User user);

    // DTO -> Entity
    @Mapping(target = "department", ignore = true) // set sau nếu cần
    @Mapping(target = "role", ignore = true)       // set sau nếu cần
    User toEntity(UserDTO dto);
}