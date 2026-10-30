package com.empresa.shifts.profession;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.empresa.shifts.shared.RecordStatus;
import com.empresa.shifts.shift.ShiftTemplate;

import jakarta.persistence.*;

@Entity 
@Table (name ="profession")
public class Profession {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, length = 80, unique = true)
    private String name;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "default_shift_template_id", nullable = false)
    private ShiftTemplate defaultShiftTemplate;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false, length = 10)
    private RecordStatus status = RecordStatus.ACTIVE;

    @Column (name = "created_by")
    private Long createdBy;

    @Column (name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column (name = "updated_by")
    private Long updatedBy; 

    @Column (name = "updated_at")
    private OffsetDateTime updatedAt;

    protected Profession() {

    }

    public Profession(String name, ShiftTemplate defaultShiftTemplate) {
        this.name = name;
        this.defaultShiftTemplate = defaultShiftTemplate;
    }

    @PrePersist 
    void onCreate() {
        this.createdAt = OffsetDateTime.now(ZoneOffset.UTC);
    }

    @PreUpdate 
    void onUpdate() {
        this.updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ShiftTemplate getDefaultShiftTemplate() {
        return defaultShiftTemplate;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDefaultShiftTemplate(ShiftTemplate defaultShiftTemplate) {
        this.defaultShiftTemplate = defaultShiftTemplate;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    
}