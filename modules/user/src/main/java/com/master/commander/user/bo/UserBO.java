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

}