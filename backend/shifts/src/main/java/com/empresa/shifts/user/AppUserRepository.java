package com.empresa.shifts.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;


public interface AppUserRepository  extends JpaRepository<AppUser, Long>{
    Optional<AppUser> findByKeycloakId(UUID keycloakId);
    boolean existsByEmailIgnoreCase(String email);
}
