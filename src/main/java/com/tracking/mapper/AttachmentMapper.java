package com.tracking.mapper;

import com.tracking.domain.Attachment;
import com.tracking.dto.AttachmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {
    AttachmentMapper INSTANCE = Mappers.getMapper(AttachmentMapper.class);

    @Mapping(source = "incident.incidentId", target = "incidentId")
    AttachmentDTO toDTO(Attachment attachment);

    @Mapping(target = "incident", ignore = true)
    Attachment toEntity(AttachmentDTO dto);
}
