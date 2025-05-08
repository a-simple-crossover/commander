package com.master.commander.user.service;

import com.master.commander.user.bo.UserBO;
import com.master.commander.user.converter.UserConverter;
import com.master.commander.user.entity.User;
import com.master.commander.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserBO> findAll() {
        return userConverter.toBOList(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    public UserBO findById(Long id) {
        return userConverter.toBO(userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id)));
    }

    @Transactional(readOnly = true)
    public UserBO findByUsername(String username) {
        return userConverter.toBO(userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username)));
    }

    @Transactional
    public UserBO create(UserBO userBO) {
        if (userRepository.existsByUsername(userBO.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + userBO.getUsername());
        }
        
        User user = userConverter.toEntity(userBO);
        user.setPassword(passwordEncoder.encode(userBO.getPassword()));
        return userConverter.toBO(userRepository.save(user));
    }

    @Transactional
    public UserBO update(Long id, UserBO userBO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        
        if (!user.getUsername().equals(userBO.getUsername()) && 
            userRepository.existsByUsername(userBO.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + userBO.getUsername());
        }

        user.setUsername(userBO.getUsername());
        user.setEmail(userBO.getEmail());
        user.setFullName(userBO.getFullName());
        user.setEnabled(userBO.isEnabled());
        
        if (userBO.getPassword() != null && !userBO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userBO.getPassword()));
        }

        if (userBO.getRoles() != null) {
            user.setRoles(userBO.getRoles());
        }

        return userConverter.toBO(userRepository.save(user));
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public UserBO updatePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Invalid old password");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        return userConverter.toBO(userRepository.save(user));
    }
} 