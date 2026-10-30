package com.empresa.shifts.profession.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProfessionRequest(    
    @NotBlank @Size (max = 80) String name,
    @NotNull Long defaultShiftTemplateId
) {
}
