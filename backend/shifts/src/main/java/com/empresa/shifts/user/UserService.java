package com.empresa.shifts.user;

import java.util.List;
import java.util.UUID;

import org.apache.catalina.WebResourceRoot.ResourceSetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empresa.shifts.profession.Profession;
import com.empresa.shifts.profession.ProfessionRepository;
import com.empresa.shifts.shared.RecordStatus;
import com.empresa.shifts.shared.exception.BusinessRuleException;
import com.empresa.shifts.shared.exception.ResourceNotFoundException;
import com.empresa.shifts.user.dto.CreateUserRequest;
import com.empresa.shifts.user.dto.UserResponse;



@Service 
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    
    private final AppUserRepository repository;
    private final AccessProfileRepository accessProfileRepository;
    private final ProfessionRepository professionRepository;
    private final KeycloakAdminClient keycloak;

    public UserService(AppUserRepository repository, 
                       AccessProfileRepository accessProfileRepository,
                       ProfessionRepository professionRepository,
                       KeycloakAdminClient keycloak
    ) {
        this.repository = repository;
        this.accessProfileRepository = accessProfileRepository;
        this.professionRepository = professionRepository;
        this.keycloak = keycloak;
    }

    @Transactional (readOnly = true)
    public List<UserResponse> findAll() {
        return repository.findAll().stream().map(UserResponse::from).toList();
    }

    /* Também cria a conta no primeiro acesso (a pessoa só chega aqui despo de definir a senha) */
    @Transactional (readOnly = true)
    public UserResponse findByKeycloakId(UUID keycloakId) {
        AppUser user = repository.findByKeycloakId(keycloakId)
            .orElseThrow(() -> new ResourceNotFoundException(
                "O usuário autentiado ainda não está cadastrado no sistema.")
            );
        if(user.getStatus() == UserStatus.PENDING_ACTIVATION) {
            user.setStatus(UserStatus.ACTIVE);
        }
        return UserResponse.from(user);
    }

    @Transactional 
    public UserResponse create(CreateUserRequest request) {
        String email = request.email().trim().toLowerCase();

        if(repository.existsByEmailIgnoreCase(email)) {
            throw new BusinessRuleException("Já existe um usuário com o e-mail: " +email+" cadastrado!");
        }

        AccessProfile profile = accessProfileRepository.findByName(request.accessProfile())
            .filter(p -> p.getStatus() == RecordStatus.ACTIVE)
            .orElseThrow(() -> new BusinessRuleException("Perfil de acesso inválido ou inativo"));
        Profession profession = professionRepository.findById(request.professionId())
            .filter(p -> p.getStatus() == RecordStatus.ACTIVE)
            .orElseThrow(() -> new BusinessRuleException("Profissão inexistente ou inativa"));

        String[]  names = splitName(request.name());
        UUID keycloakId = keycloak.createUser(email, names[0], names[1]);

        try {
            keycloak.assignRealmRole(keycloakId, profile.getName());

            AppUser user = new AppUser(keycloakId, request.name().trim(), email, profile ,profession );
            user.setPhone(request.phone());
            user.setRegistrationNumber(request.registrationNumber());
            user.setShiftTemplate(profession.getDefaultShiftTemplate());
            AppUser saved = repository.saveAndFlush(user);

            keycloak.sendActivationEmail(keycloakId);
            return UserResponse.from(saved);

        } catch (RuntimeException e) {
           compensate(keycloakId);
           throw e;
        }
    }

        /* Defaz a criação no Keycloak; o banco local já faz o rollback sozinho */
        private void compensate(UUID keycloakId) {
            try {
                keycloak.deleteUser(keycloakId);
            } catch (Exception e) {
                log.error("Falha ao desfazer usáriuo {} no Keycloak. Limpeza manual necssário");
            }
        }

        private static String[] splitName(String fullName) {
            String[] parts = fullName.trim().split("\\s+",2);
            return new String[]{parts[0], parts.length > 1 ? parts[1] : "-"};
        }
}
