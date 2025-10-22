package com.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer userId;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private Integer departmentId;
    private String departmentName;
    private Integer roleId;
    private String roleName;
}
