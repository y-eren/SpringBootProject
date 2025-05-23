package com.imageprocessing.imageprocessproject.service;


import com.imageprocessing.imageprocessproject.model.User;
import com.imageprocessing.imageprocessproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User registerUser(User user) {
        String hashPwd = passwordEncoder.encode(user.getPwd());
        user.setPwd(hashPwd);
        user.setUsername(user.getUsername());
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
}
