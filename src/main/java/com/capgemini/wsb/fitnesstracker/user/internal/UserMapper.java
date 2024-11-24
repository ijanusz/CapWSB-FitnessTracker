package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.dto.SearchUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.dto.SimpleUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.model.User;
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

    SimpleUserDto toSimpleDto(User user) {
        return new SimpleUserDto(user.getId(), user.getFirstName(), user.getLastName());
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
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate()
        );
    }

    User toUpdateEntity(User existingUser, UserDto userDto) {
        if (userDto.firstName() != null) {
            existingUser.setFirstName(userDto.firstName());
        }
        if (userDto.lastName() != null) {
            existingUser.setLastName(userDto.lastName());
        }
        if (userDto.birthdate() != null) {
            existingUser.setBirthdate(userDto.birthdate());
        }
        if (userDto.email() != null) {
            existingUser.setEmail(userDto.email());
        }
        return existingUser;
    }
}
