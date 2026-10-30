package com.empresa.shifts.shift.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ShiftTemplateRequest(
    @NotBlank @Size(max = 80) String name,
    @NotNull  LocalTime startTime,
    @NotNull  LocalTime endTime
) {
    
}
