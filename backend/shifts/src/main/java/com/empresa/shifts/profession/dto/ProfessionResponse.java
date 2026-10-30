package com.empresa.shifts.profession.dto;

import com.empresa.shifts.profession.Profession;
import com.empresa.shifts.shared.RecordStatus;
import com.empresa.shifts.shift.ShiftTemplate;

public record ProfessionResponse(
    Long id,
    String name,
    Long defaultShiftTemplateId,
    String defaultShiftTemplateName,
    RecordStatus status
) {
    public static ProfessionResponse from (Profession p) {
        ShiftTemplate template = p.getDefaultShiftTemplate();
        return new ProfessionResponse(p.getId(), p.getName(), 
        template != null ? template.getId() : null,
        template != null ? template.getName() : null
        ,p.getStatus());
    }
    
}
