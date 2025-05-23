package com.imageprocessing.imageprocessproject.controller;

import com.imageprocessing.imageprocessproject.dto.ResponseDTO;
import com.imageprocessing.imageprocessproject.helper.TokenHolder;
import com.imageprocessing.imageprocessproject.model.User;
import com.imageprocessing.imageprocessproject.repository.UserRepository;
import com.imageprocessing.imageprocessproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User successfully registered");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Bir hata meydana geldi" + e.getMessage());
        }

    }

    @RequestMapping("/user")
    public ResponseEntity<ResponseDTO> login(Authentication authentication) {
         User user = userRepository.findByUsername(authentication.getName()).orElse(null);
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseDTO(TokenHolder.getToken(), user));
    }
}
