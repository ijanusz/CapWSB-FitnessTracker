package com.capgemini.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;
import lombok.Builder;

@Builder
public record SimpleUserDto(@Nullable Long id, String firstName, String lastName) {
    public static SimpleUserDto from(User user) {
        return SimpleUserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
