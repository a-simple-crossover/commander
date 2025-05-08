package com.master.commander.user.controller;

import com.master.commander.user.bo.UserBO;
import com.master.commander.user.converter.UserConverter;
import com.master.commander.user.dto.UserDTO;
import com.master.commander.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserConverter userConverter;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserBO> users = userService.findAll();
        return ResponseEntity.ok(userConverter.toDTOList(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserBO user = userService.findById(id);
        return ResponseEntity.ok(userConverter.toDTO(user));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        UserBO user = userService.findByUsername(username);
        return ResponseEntity.ok(userConverter.toDTO(user));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserBO userBO = userConverter.toBO(userDTO);
        UserBO createdUser = userService.create(userBO);
        return new ResponseEntity<>(userConverter.toDTO(createdUser), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserBO userBO = userConverter.toBO(userDTO);
        UserBO updatedUser = userService.update(id, userBO);
        return ResponseEntity.ok(userConverter.toDTO(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<UserDTO> updatePassword(
            @PathVariable Long id,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        UserBO updatedUser = userService.updatePassword(id, oldPassword, newPassword);
        return ResponseEntity.ok(userConverter.toDTO(updatedUser));
    }
} 