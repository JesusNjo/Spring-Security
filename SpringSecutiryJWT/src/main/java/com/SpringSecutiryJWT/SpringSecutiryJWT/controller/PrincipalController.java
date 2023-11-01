package com.SpringSecutiryJWT.SpringSecutiryJWT.controller;


import com.SpringSecutiryJWT.SpringSecutiryJWT.controller.request.CreateUserDTO;
import com.SpringSecutiryJWT.SpringSecutiryJWT.models.ERole;
import com.SpringSecutiryJWT.SpringSecutiryJWT.models.RoleEntity;
import com.SpringSecutiryJWT.SpringSecutiryJWT.models.UserEntity;
import com.SpringSecutiryJWT.SpringSecutiryJWT.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class PrincipalController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/hello")
    public String hellow() {
        return "Hello World NOT SECURED";
    }

    @GetMapping("/helloSecured")
    public String hellowSecured() {
        return "Hello World SECURED";
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {


        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(r -> RoleEntity.builder()
                        .name(ERole.valueOf(r))
                        .build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .email(createUserDTO.getEmail())
                .roles(roles)
                .build();
        userRepository.save(userEntity);
        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam String id) {
        userRepository.deleteById(Long.parseLong(id));
        return "Se ha borrado el user con id ".concat(id);
    }
}

