package com.empresa.shifts.user;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empresa.shifts.shared.exception.ResourceNotFoundException;
import com.empresa.shifts.user.dto.UserResponse;



@Service 
public class UserService {
    
    private final AppUserRepository repository;

    public UserService(AppUserRepository repository) {
        this.repository = repository;
    }

    @Transactional (readOnly = true)
    public UserResponse findByKeycloakId(UUID keycloakId) {
        return repository.findByKeycloakId(keycloakId)
            .map(UserResponse::from)
            .orElseThrow(() -> new ResourceNotFoundException(
                "O usuário autentiado ainda não está cadastrado no sistema.")
            );
    }

}
