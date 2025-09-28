package com.tracking.service.impl;

import com.tracking.domain.Role;
import com.tracking.dto.RoleDTO;
import com.tracking.mapper.RoleMapper;
import com.tracking.repository.RoleRepository;
import com.tracking.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleMapper.toDTOList(roleRepository.findAll());
    }

    @Override
    public RoleDTO getRoleById(Integer id) {
        return roleRepository.findById(id)
                .map(roleMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Role not found with id " + id));
    }

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = roleMapper.toEntity(roleDTO);
        return roleMapper.toDTO(roleRepository.save(role));
    }

    @Override
    public RoleDTO updateRole(Integer id, RoleDTO roleDTO) {
        return roleRepository.findById(id)
                .map(existing -> {
                    existing.setRoleName(roleDTO.getRoleName());
                    existing.setDescription(roleDTO.getDescription());
                    return roleMapper.toDTO(roleRepository.save(existing));
                })
                .orElseThrow(() -> new RuntimeException("Role not found with id " + id));
    }

    @Override
    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllRolesAndResetId() {
        roleRepository.deleteAll();
        roleRepository.resetIdentity();
    }
}