package com.capgemini.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

public record SearchUserDto(@Nullable Long id, String email) {
}
