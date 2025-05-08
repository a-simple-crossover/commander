package com.master.commander.user.converter;

import com.master.commander.user.bo.UserBO;
import com.master.commander.user.dto.UserDTO;
import com.master.commander.user.entity.Role;
import com.master.commander.user.entity.User;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserConverter {
    // Entity -> BO
    UserBO toBO(User user);
    List<UserBO> toBOList(List<User> users);
    
    // BO -> Entity
    @Mapping(target = "id", ignore = true)
    User toEntity(UserBO userBO);
    
    // BO -> DTO
    @Mapping(target = "maskedEmail", source = "email", qualifiedByName = "maskEmail")
    @Mapping(target = "roleNames", source = "roles")
    UserDTO toDTO(UserBO userBO);
    List<UserDTO> toDTOList(List<UserBO> userBOs);
    
    // DTO -> BO
    @Mapping(target = "roles", ignore = true)
    UserBO toBO(UserDTO userDTO);

    @Named("maskEmail")
    default String maskEmail(String email) {
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

    @Named("rolesToRoleNames")
    default Set<String> rolesToRoleNames(Set<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    @AfterMapping
    default void setRoleNames(@MappingTarget UserDTO userDTO, User user) {
        if (user.getRoles() != null) {
            userDTO.setRoleNames(rolesToRoleNames(user.getRoles()));
        }
    }
} 