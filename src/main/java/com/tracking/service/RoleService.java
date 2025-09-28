package com.tracking.service;

import com.tracking.dto.RoleDTO;
import java.util.List;

public interface RoleService {
    List<RoleDTO> getAllRoles();
    RoleDTO getRoleById(Integer id);
    RoleDTO createRole(RoleDTO roleDTO);
    RoleDTO updateRole(Integer id, RoleDTO roleDTO);
    void deleteRole(Integer id);
    void deleteAllRolesAndResetId();
}
