package  com.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidentDTO {
    private Integer incidentId;
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Integer createdById;
    private String createdByName;

    private Integer assignedToId;
    private String assignedToName;

    private Integer categoryId;
    private String categoryName;
}
