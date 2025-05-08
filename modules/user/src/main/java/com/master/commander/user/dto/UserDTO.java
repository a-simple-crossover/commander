package com.master.commander.user.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String maskedEmail; // 脱敏后的邮箱
    private String fullName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean enabled;
    private Set<String> roleNames; // 只包含角色名称

    // 用于创建和更新的字段
    private String password;
    private String email;
} 