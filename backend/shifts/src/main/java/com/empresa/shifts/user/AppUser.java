package com.empresa.shifts.user;

import java.util.UUID;

import com.empresa.shifts.profession.Profession;
import com.empresa.shifts.shared.audit.AuditableEntity;
import com.empresa.shifts.shift.ShiftTemplate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table (name = "app_user")
public class AppUser extends  AuditableEntity{
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "keycloak_id", nullable = false, unique = true, updatable = false)
    private UUID keycloakId;

    @Column (nullable = false, length = 150)
    private String name;

    @Column (nullable = false, length = 150)
    private String email;

    @Column (length = 20)
    private String phone;

    @Column (name = "registration_number", length = 30)
    private String registrationNumber;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "access_profile_id")
    private AccessProfile accessProfile;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "profession_id")
    private Profession profession;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "shift_template_id")
    private ShiftTemplate shiftTemplate;
    
    @Enumerated (EnumType.STRING)
    @Column (nullable = false, length = 20)
    private UserStatus status = UserStatus.PENDING_ACTIVATION;

    protected AppUser() {
    }

    public AppUser(UUID keylcoakId, String name, String email, AccessProfile accessProfile, Profession profession) {
        this.keycloakId = keylcoakId;
        this.name = name;
        this.email = email;
        this.accessProfile = accessProfile;
        this.profession = profession;
    } 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getKeycloakId() {
        return keycloakId;
    }

    public void setKeycloakId(UUID keycloakId) {
        this.keycloakId = keycloakId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    

    public AccessProfile getAccessProfile() {
        return accessProfile;
    }

    public Profession getProfession() {
        return profession;
    }

    public ShiftTemplate getShiftTemplate() {
        return shiftTemplate;
    }

    

    public void setShiftTemplate(ShiftTemplate shiftTemplate) {
        this.shiftTemplate = shiftTemplate;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
    
    
}
