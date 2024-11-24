package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.dto.SearchUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.dto.SimpleUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.exception.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.exception.UserWithMailNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.model.User;
import com.capgemini.wsb.fitnesstracker.user.internal.searchStrategy.OlderThanAgeStrategy;
import com.capgemini.wsb.fitnesstracker.user.internal.searchStrategy.SearchEmailStrategy;
import com.capgemini.wsb.fitnesstracker.user.internal.searchStrategy.UserFilterStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/simple")
    public List<SimpleUserDto> getSimpleUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable("userId") Long userId) {
        return userService.getUser(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody UserDto userDto) {
        System.out.println("User with e-mail: " + userDto.email() + " passed to the request");

        return userService.createUser(userMapper.toEntity(userDto));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public long removeUser(@PathVariable("userId") Long userId) {
        userService.removeUser(userId);
        return userId;
    }

    // Testowanie przez search param: http://localhost:8080/v1/users/find?age=50&email=ethan
    @GetMapping("/find")
    public List<SearchUserDto> findUsersByParams(@RequestParam(required = false) String email,
                                                 @RequestParam(required = false) Integer age) {

        List<UserFilterStrategy> strategies = List.of(
                new OlderThanAgeStrategy(age),
                new SearchEmailStrategy(email)
        );

        return userService
                .findAllUsers()
                .stream()
                .filter(user -> applyStrategies(strategies, user)
                        .allMatch(strat -> strat.filter(user)))
                .map(userMapper::toSearchUserDto)
                .toList();
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return userService.getUser(userId)
                .map(existingUser -> userMapper.toUpdateEntity(existingUser, userDto))
                .map(userService::updateUser)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @GetMapping("/email")
    public List<UserDto> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email)
                .map(user -> List.of(userMapper.toDto(user)))
                .orElseThrow(() -> new UserWithMailNotFoundException(email));
    }

    @GetMapping("/older/{date}")
    public List<UserDto> getAllUsersOlderThan(@PathVariable("date") LocalDate date) {
        return userService.findAllUsers().stream()
                .filter(user -> user.getBirthdate().isBefore(date))
                .map(userMapper::toDto)
                .toList();
    }

    private Stream<UserFilterStrategy> applyStrategies(List<UserFilterStrategy> strategies, User user) {
        return strategies.stream().filter(strat -> strat.isApplicable(user));
    }
}