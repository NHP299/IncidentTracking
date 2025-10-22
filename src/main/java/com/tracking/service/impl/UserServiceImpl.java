package com.tracking.service.impl;

import com.tracking.domain.Department;
import com.tracking.domain.Role;
import com.tracking.domain.User;
import com.tracking.dto.UserDTO;
import com.tracking.mapper.UserMapper;
import com.tracking.repository.DepartmentRepository;
import com.tracking.repository.RoleRepository;
import com.tracking.repository.UserRepository;
import com.tracking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public UserDTO getUserById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(userDTO.getPassword()));
        }
        if (userDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(userDTO.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            user.setDepartment(department);
        }

        if (userDTO.getRoleId() != null) {
            Role role = roleRepository.findById(userDTO.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.setRole(role);
        }

        return userMapper.toDTO(userRepository.save(user));
    }


    @Override
    public UserDTO updateUser(Integer id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userDTO.getUsername());
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());

        if (userDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(userDTO.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            user.setDepartment(department);
        }

        if (userDTO.getRoleId() != null) {
            Role role = roleRepository.findById(userDTO.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.setRole(role);
        }

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(userDTO.getPassword()));
        }

        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
        if (userRepository.count() == 0) {
            userRepository.resetIdentity();
        }
    }
}
