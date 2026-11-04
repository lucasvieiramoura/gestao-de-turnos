package com.empresa.shifts.shift;

import com.empresa.shifts.shared.RecordStatus;
import com.empresa.shifts.shared.audit.AuditableEntity;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity 
@Table (name = "shift_template")
public class ShiftTemplate extends AuditableEntity {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, length = 80)
    private String name;

    @Column (name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column (name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column (name = "duration_minutes", nullable = false)
    private int durationMinutes;

    @Column (name = "crosses_midnight", nullable = false)
    private boolean crossesMidnight;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false, length = 10)
    private RecordStatus status = RecordStatus.ACTIVE;


    protected ShiftTemplate () {
        // exigido pelo JPA; protected para ninguém criar objeto "vazio" por engano
    }

    public ShiftTemplate (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public boolean isCrossesMidnight() {
        return crossesMidnight;
    }

    public void setCrossesMidnight(boolean crossesMidnight) {
        this.crossesMidnight = crossesMidnight;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

}
