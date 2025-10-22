package com.tracking.mapper;

import  com.tracking.domain.User;
import  com.tracking.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "department.departmentId", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(source = "role.roleId", target = "roleId")
    @Mapping(source = "role.roleName", target = "roleName")
    UserDTO toDTO(User user);

    @Mapping(target = "department", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(UserDTO dto);
}
