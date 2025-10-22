package com.tracking.service.impl;

import com.tracking.domain.Department;
import com.tracking.dto.DepartmentDTO;
import com.tracking.mapper.DepartmentMapper;
import com.tracking.repository.DepartmentRepository;
import com.tracking.service.DepartmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(department -> {
                    DepartmentDTO dto = new DepartmentDTO();
                    dto.setDepartmentId(department.getDepartmentId());
                    dto.setDepartmentName(department.getName());
                    dto.setDescription(department.getDescription());
                    return dto;
                })
                .toList();
    }

    @Override
    public DepartmentDTO getDepartmentById(Integer id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        DepartmentDTO dto = new DepartmentDTO();
        dto.setDepartmentId(department.getDepartmentId());
        dto.setDepartmentName(department.getName());
        dto.setDescription(department.getDescription());
        return dto;
    }

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO dto) {
        Department department = new Department();
        department.setName(dto.getDepartmentName());
        department.setDescription(dto.getDescription());
        Department saved = departmentRepository.save(department);

        DepartmentDTO response = new DepartmentDTO();
        response.setDepartmentId(saved.getDepartmentId());
        response.setDepartmentName(saved.getName());
        response.setDescription(saved.getDescription());
        return response;
    }

    @Override
    public DepartmentDTO updateDepartment(Integer id, DepartmentDTO dto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        department.setName(dto.getDepartmentName());
        department.setDescription(dto.getDescription());
        Department updated = departmentRepository.save(department);

        DepartmentDTO response = new DepartmentDTO();
        response.setDepartmentId(updated.getDepartmentId());
        response.setDepartmentName(updated.getName());
        response.setDescription(updated.getDescription());
        return response;
    }

    @Override
    @Transactional
    public void deleteDepartment(Integer id) {
        departmentRepository.deleteById(id);

        if (departmentRepository.count() == 0) {
            departmentRepository.resetIdentity();
        }
    }
}
