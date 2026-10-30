package com.empresa.shifts.shift.dto;

import java.time.LocalTime;

import com.empresa.shifts.shared.RecordStatus;
import com.empresa.shifts.shift.ShiftTemplate;

public record ShiftTemplateResponse(
    Long id,
    String name,
    LocalTime startTime,
    LocalTime endTime,
    int durationMinutes,
    boolean crossesMidnight,
    RecordStatus status
) {
    public static ShiftTemplateResponse from(ShiftTemplate t) {
        return new ShiftTemplateResponse(
            t.getId(), t.getName(), t.getStartTime(), t.getEndTime(),
            t.getDurationMinutes(), t.isCrossesMidnight(), t.getStatus()
        );
    }
}
