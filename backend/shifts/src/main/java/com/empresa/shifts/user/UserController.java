package com.empresa.shifts.user;

import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.shifts.user.dto.UserResponse;

@RestController 
@RequestMapping ("/api/users")
public class UserController {
    
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping ("/me")
    public UserResponse me(@AuthenticationPrincipal Jwt jwt) {
        return service.findByKeycloakId(UUID.fromString(jwt.getSubject()));
    }


}
