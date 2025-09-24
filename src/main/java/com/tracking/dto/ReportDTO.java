package  com.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    private Integer reportId;
    private String title;

    private Integer generatedById;
    private String generatedByName;

    private LocalDateTime generatedAt;
    private String filePath;
}