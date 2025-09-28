package com.tracking.mapper;

import com.tracking.domain.Category;
import com.tracking.domain.Incident;
import com.tracking.domain.User;
import com.tracking.dto.IncidentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IncidentMapper {

    // Entity -> DTO
    @Mapping(source = "createdBy.userId", target = "createdById")
    @Mapping(source = "createdBy.fullName", target = "createdByFullName")
    @Mapping(source = "assignedTo.userId", target = "assignedToId")
    @Mapping(source = "assignedTo.fullName", target = "assignedToFullName")
    @Mapping(source = "category.categoryId", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    IncidentDTO toDTO(Incident incident);

    List<IncidentDTO> toDTOList(List<Incident> incidents);

    // DTO -> Entity
    @Mapping(source = "createdById", target = "createdBy", qualifiedByName = "mapUser")
    @Mapping(source = "assignedToId", target = "assignedTo", qualifiedByName = "mapUser")
    @Mapping(source = "categoryId", target = "category", qualifiedByName = "mapCategory")
    Incident toEntity(IncidentDTO dto);

    // helper methods để tạo User/Category rỗng với id
    @Named("mapUser")
    default User mapUser(Integer id) {
        if (id == null) return null;
        User u = new User();
        u.setUserId(id);
        return u;
    }

    @Named("mapCategory")
    default Category mapCategory(Integer id) {
        if (id == null) return null;
        Category c = new Category();
        c.setCategoryId(id);
        return c;
    }
}