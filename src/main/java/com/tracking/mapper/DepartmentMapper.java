package  com.tracking.mapper;

import  com.tracking.domain.Department;
import  com.tracking.dto.DepartmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    DepartmentDTO toDTO(Department department);

    Department toEntity(DepartmentDTO dto);
}
