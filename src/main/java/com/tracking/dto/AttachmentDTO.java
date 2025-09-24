package  com.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class AttachmentDTO {
    private Integer attachmentId;
    private Integer incidentId;
    private String filePath;
    private LocalDateTime uploadedAt;
}