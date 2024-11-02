package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.SearchUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.SimpleUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.internal.searchStrategy.CompositeUserFilterStrategy;
import com.capgemini.wsb.fitnesstracker.user.internal.searchStrategy.OlderThanAgeStrategy;
import com.capgemini.wsb.fitnesstracker.user.internal.searchStrategy.SearchEmailStrategy;
import com.capgemini.wsb.fitnesstracker.user.internal.searchStrategy.UserFilterStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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

    @GetMapping("/simple-users")
    public List<SimpleUserDto> getSimpleUsers() {
        return userService.findAllUsers()
                .stream()
                .map(SimpleUserDto::from)
                .toList();
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable("userId") Long userId) {
        return userService.getUser(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @PostMapping
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {
        System.out.println("User with e-mail: " + userDto.email() + " passed to the request");

        return userService.createUser(userMapper.toEntity(userDto));
    }

    @DeleteMapping("/{userId}")
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

    @PutMapping
    public User updateUser(@RequestBody UserDto userDto) {

        ''
    }

    private Stream<UserFilterStrategy> applyStrategies(List<UserFilterStrategy> strategies, User user) {
        return strategies.stream().filter(strat -> strat.isApplicable(user));
    }
}