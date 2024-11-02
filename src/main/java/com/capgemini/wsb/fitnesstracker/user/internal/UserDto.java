package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import lombok.Builder;

import java.time.LocalDate;

@Builder
record UserDto(@Nullable Long Id, String firstName, String lastName,
               @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
               String email) {

    public static UserDto from(User foundUser, UserDto userDto) {
        return UserDto.builder()
                .Id(foundUser.getId())
                .firstName(userDto.firstName() != null ? userDto.firstName() : foundUser.getFirstName())
                .lastName(userDto.lastName() != null ? userDto.lastName() : foundUser.getLastName())
                .birthdate(userDto.birthdate() != null ? userDto.birthdate() : foundUser.getBirthdate())
                .email(userDto.email() != null ? userDto.email : foundUser.getEmail())
                .build();
    }
}
