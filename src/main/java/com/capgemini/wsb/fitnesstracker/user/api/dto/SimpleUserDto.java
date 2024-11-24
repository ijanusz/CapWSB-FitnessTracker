package com.capgemini.wsb.fitnesstracker.user.api.dto;

import jakarta.annotation.Nullable;

public record SimpleUserDto(@Nullable Long id,
                            String firstName,
                            String lastName) {
}
