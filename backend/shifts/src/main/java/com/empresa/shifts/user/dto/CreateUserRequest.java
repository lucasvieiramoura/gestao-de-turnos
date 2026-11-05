package com.empresa.shifts.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
    @NotBlank @Size (max = 150) String name,
    @NotBlank @Email  @Size (max = 150) String email,
    @Size (max = 20) String phone,
    @Size (max = 30) String registrationNumber,
    @NotBlank @Pattern (regexp = "ADMIN|CORDINATOR|PROFESSIONAL") String accessProfile,
    @NotNull Long professionId
) {
}
