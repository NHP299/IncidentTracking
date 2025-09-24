package  com.tracking.mapper;

import  com.tracking.domain.Comment;
import  com.tracking.dto.CommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "incident.incidentId", target = "incidentId")
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.fullName", target = "userName")
    CommentDTO toDTO(Comment comment);

    @Mapping(target = "incident", ignore = true)
    @Mapping(target = "user", ignore = true)
    Comment toEntity(CommentDTO dto);
}
