package  com.tracking.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Integer commentId;
    private Integer incidentId;
    private Integer userId;
    private String userName;
    private String userFullName;
    private String content;
    private LocalDateTime createdAt;
}