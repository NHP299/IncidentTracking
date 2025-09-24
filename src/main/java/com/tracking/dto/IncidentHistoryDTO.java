package  com.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidentHistoryDTO {
    private Integer historyId;
    private Integer incidentId;
    private String status;

    private Integer changedById;
    private String changedByName;

    private LocalDateTime changedAt;
    private String note;
}