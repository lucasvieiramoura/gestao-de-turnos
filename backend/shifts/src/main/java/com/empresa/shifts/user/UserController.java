package com.empresa.shifts.user;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.shifts.user.dto.CreateUserRequest;
import com.empresa.shifts.user.dto.UserResponse;

import jakarta.validation.Valid;

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

    @GetMapping
    @PreAuthorize ("hasAnyRole('ADMIN','COORDINATOR')")
    public List<UserResponse> list() {
        return service.findAll();
    }

    @PostMapping 
    @PreAuthorize ("hasRole('ADMIM') or (hasRole('COORDINATOR) and #request.accessProfile() == 'PROFESSIONAL'")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        UserResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/users/"+ created.id())).body(created);
    }

}
