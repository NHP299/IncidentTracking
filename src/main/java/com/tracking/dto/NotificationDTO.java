package  com.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private Integer notificationId;
    private Integer userId;
    private String userFullName;

    private Integer incidentId;
    private String message;
    private Boolean isRead;
    private LocalDateTime createdAt;
}