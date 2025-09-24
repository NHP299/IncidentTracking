package  com.tracking.mapper;

import  com.tracking.domain.Notification;
import  com.tracking.dto.NotificationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "incident.incidentId", target = "incidentId")
    NotificationDTO toDTO(Notification notification);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "incident", ignore = true)
    Notification toEntity(NotificationDTO dto);
}
