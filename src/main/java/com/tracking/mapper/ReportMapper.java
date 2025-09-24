package  com.tracking.mapper;

import  com.tracking.domain.Report;
import  com.tracking.dto.ReportDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    ReportMapper INSTANCE = Mappers.getMapper(ReportMapper.class);

    @Mapping(source = "generatedBy.userId", target = "generatedById")
    @Mapping(source = "generatedBy.fullName", target = "generatedByName")
    ReportDTO toDTO(Report report);

    @Mapping(target = "generatedBy", ignore = true)
    Report toEntity(ReportDTO dto);
}
