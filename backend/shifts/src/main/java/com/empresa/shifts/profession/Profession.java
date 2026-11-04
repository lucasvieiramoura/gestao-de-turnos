package com.empresa.shifts.profession;

import com.empresa.shifts.shared.RecordStatus;
import com.empresa.shifts.shared.audit.AuditableEntity;
import com.empresa.shifts.shift.ShiftTemplate;

import jakarta.persistence.*;

@Entity 
@Table (name ="profession")
public class Profession extends AuditableEntity{

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

    protected Profession() {

    }

    public Profession(String name, ShiftTemplate defaultShiftTemplate) {
        this.name = name;
        this.defaultShiftTemplate = defaultShiftTemplate;
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