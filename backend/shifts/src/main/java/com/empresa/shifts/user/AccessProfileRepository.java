package com.empresa.shifts.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessProfileRepository  extends JpaRepository<AccessProfile, Long> {
    Optional<AccessProfile> findByName(String name);
}
