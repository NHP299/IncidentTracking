package  com.tracking.mapper;

import  com.tracking.domain.Incident;
import  com.tracking.dto.IncidentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IncidentMapper {
    IncidentMapper INSTANCE = Mappers.getMapper(IncidentMapper.class);

    @Mapping(source = "createdBy.userId", target = "createdById")
    @Mapping(source = "createdBy.fullName", target = "createdByName")
    @Mapping(source = "assignedTo.userId", target = "assignedToId")
    @Mapping(source = "assignedTo.fullName", target = "assignedToName")
    @Mapping(source = "category.categoryId", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    IncidentDTO toDTO(Incident incident);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "category", ignore = true)
    Incident toEntity(IncidentDTO dto);
}
