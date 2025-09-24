package com.tracking.mapper;

import com.tracking.domain.IncidentHistory;
import com.tracking.dto.IncidentHistoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IncidentHistoryMapper {
    IncidentHistoryMapper INSTANCE = Mappers.getMapper(IncidentHistoryMapper.class);

    @Mapping(source = "incident.incidentId", target = "incidentId")
    @Mapping(source = "changedBy.userId", target = "changedById")
    @Mapping(source = "changedBy.fullName", target = "changedByName")
    IncidentHistoryDTO toDTO(IncidentHistory history);

    @Mapping(target = "incident", ignore = true)
    @Mapping(target = "changedBy", ignore = true)
    IncidentHistory toEntity(IncidentHistoryDTO dto);
}
