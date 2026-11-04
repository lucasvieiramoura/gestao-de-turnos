package com.empresa.shifts.shared.audit;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.domain.AuditorAware;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component 
public class SecurityAuditorAware implements AuditorAware<Long> {

    private final JdbcClient jdbc;
    private final Map<UUID, Long> cache = new ConcurrentHashMap<>();

    public SecurityAuditorAware(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }
    
    @Override 
    public Optional<Long> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof Jwt jwt)) {
            return Optional.empty();
        } 

        UUID keycloakId;
        try {
            keycloakId = UUID.fromString(jwt.getSubject());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }

        Long cached = cache.get(keycloakId);
        if (cached != null ) {
            return Optional.of(cached);
        }

        Optional<Long> userId = jdbc.sql("select id from app_user where keycloak_id = :k")
            .param("k", keycloakId)
            .query(Long.class)
            .optional();
        userId.ifPresent(id -> cache.put(keycloakId, id));
        return  userId;

    }
}
