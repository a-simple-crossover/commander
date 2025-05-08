package com.master.commander.user.bo;

import com.master.commander.user.entity.Role;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserBO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean enabled;
    private Set<Role> roles = new HashSet<>();

    // 邮箱脱敏方法
    public String getMaskedEmail() {
        if (email == null) {
            return null;
        }
        
        int atIndex = email.indexOf('@');
        if (atIndex > 1) {
            String name = email.substring(0, atIndex);
            String domain = email.substring(atIndex);
            
            return name.substring(0, Math.min(3, name.length())) + 
                   "*".repeat(Math.max(0, name.length() - 3)) + 
                   domain;
        }
        return email;
    }
} 