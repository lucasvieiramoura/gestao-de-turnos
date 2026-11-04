package com.empresa.shifts.user;

import com.empresa.shifts.shared.RecordStatus;
import com.empresa.shifts.shared.audit.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table (name = "access_profile")
public class AccessProfile  extends  AuditableEntity{
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, length = 50, unique = true)
    private String name;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false, length = 10)
    private RecordStatus status = RecordStatus.ACTIVE;

    protected  AccessProfile() {
    }

    public Long getId() { return  id; } 
    public  String getName() { return  name; }
    public  RecordStatus getStatus() { return status; }    
}
