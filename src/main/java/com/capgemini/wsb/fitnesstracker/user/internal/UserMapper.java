package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.SearchUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.SimpleUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

@Component
class UserMapper {

    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                           user.getFirstName(),
                           user.getLastName(),
                           user.getBirthdate(),
                           user.getEmail());
    }

    User toEntity(UserDto userDto) {
        return new User(
                        userDto.firstName(),
                        userDto.lastName(),
                        userDto.birthdate(),
                        userDto.email());
    }

    SearchUserDto toSearchUserDto(User user) {
        return new SearchUserDto(
                user.getId(),
                user.getEmail()
        );
    }
}
