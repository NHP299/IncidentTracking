package com.tracking.service;

import com.tracking.dto.DepartmentDTO;
import java.util.List;

public interface DepartmentService {
    List<DepartmentDTO> getAllDepartments();
    DepartmentDTO getDepartmentById(Integer id);
    DepartmentDTO createDepartment(DepartmentDTO dto);
    DepartmentDTO updateDepartment(Integer id, DepartmentDTO dto);
    void deleteDepartment(Integer id);
}
