package com.capgemini.wsb.fitnesstracker.user.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record SearchUserDto(@Nullable Long id,
                            String email,
                            @Nullable String firstName,
                            @Nullable String lastName,
                            @Nullable @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate) {
}
