package com.empresa.shifts.config;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping ("/api/me")
public class MeController {
    
    @GetMapping 
    public Map<String, Object> me(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        List<String> roles = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .toList();
        return Map.of(
            "subject", jwt.getSubject(),
            "username", String.valueOf(jwt.getClaimAsString("preferred_username")),
            "email", String.valueOf(jwt.getClaimAsString("email")),
            "roles", roles);
    }
}
