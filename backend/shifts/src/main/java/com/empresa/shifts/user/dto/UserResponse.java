package com.empresa.shifts.user.dto;

import com.empresa.shifts.user.AppUser;
import com.empresa.shifts.user.UserStatus;

public record UserResponse(
    Long id,
    String name,
    String email,
    String phone,
    String accessProfile,
    String profession,
    UserStatus status
) {
    public static UserResponse from (AppUser u) {
        return new UserResponse(
            u.getId(), u.getName(), u.getEmail(), u.getPhone(),
            u.getAccessProfile().getName(),
            u.getProfession().getName(),
            u.getStatus()
        );
    }
}
