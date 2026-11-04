package com.empresa.shifts.shared.audit;

import java.time.OffsetDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass 
@EntityListeners (AuditingEntityListener.class)
public abstract class AuditableEntity {

    @CreatedDate 
    @Column (name ="created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @CreatedBy 
    @Column (name = "created_by", updatable = false)
    private Long createdBy;

    @LastModifiedDate 
    @Column (name = "updated_at")
    private OffsetDateTime updatedAt;

    @LastModifiedBy 
    @Column (name = "updated_by")
    private Long updatedBy;

    public OffsetDateTime getCreatedAt() {return createdAt; }
    public Long getCreatedBy() { return createdBy; }
    public OffsetDateTime getUpdateAt() { return updatedAt; }
    public Long getUpdateBy() { return updatedBy; }

}