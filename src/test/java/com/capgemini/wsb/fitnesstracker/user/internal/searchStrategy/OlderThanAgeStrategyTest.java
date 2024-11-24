package com.capgemini.wsb.fitnesstracker.user.internal.searchStrategy;

import com.capgemini.wsb.fitnesstracker.user.api.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OlderThanAgeStrategyTest {

    @Test
    void shouldReturnTrueWhenUserIsOlderThanAge() {
        // given
        User user = new User(randomUUID().toString(), randomUUID().toString(), LocalDate.now(), randomUUID().toString());
        user.setBirthdate(LocalDate.of(1990, 1, 1));
        OlderThanAgeStrategy strategy = new OlderThanAgeStrategy(30);

        // when
        boolean result = strategy.filter(user);

        // then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenUserIsNotOlderThanAge() {
        // given
        User user = new User(randomUUID().toString(), randomUUID().toString(), LocalDate.now(), randomUUID().toString());
        user.setBirthdate(LocalDate.of(2000, 1, 1));
        OlderThanAgeStrategy strategy = new OlderThanAgeStrategy(30);

        // when
        boolean result = strategy.filter(user);

        // then
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseWhenAgeIsNull() {
        // given
        User user = new User(randomUUID().toString(), randomUUID().toString(), LocalDate.now(), randomUUID().toString());
        user.setBirthdate(LocalDate.of(2000, 1, 1));
        OlderThanAgeStrategy strategy = new OlderThanAgeStrategy(null);

        // when
        boolean result = strategy.filter(user);

        // then
        assertFalse(result);
    }

    @Test
    void shouldBeApplicableWhenUserAndAgeAreNotNull() {
        // given
        User user = new User(randomUUID().toString(), randomUUID().toString(), LocalDate.now(), randomUUID().toString());
        user.setBirthdate(LocalDate.of(1990, 1, 1));
        OlderThanAgeStrategy strategy = new OlderThanAgeStrategy(30);

        // when
        boolean result = strategy.isApplicable(user);

        // then
        assertTrue(result);
    }

    @Test
    void shouldNotBeApplicableWhenUserIsNull() {
        // given
        OlderThanAgeStrategy strategy = new OlderThanAgeStrategy(30);

        // when
        boolean result = strategy.isApplicable(null);

        // then
        assertFalse(result);
    }

    @Test
    void shouldNotBeApplicableWhenAgeIsNull() {
        // given
        User user = new User(randomUUID().toString(), randomUUID().toString(), LocalDate.now(), randomUUID().toString());
        user.setBirthdate(LocalDate.of(1990, 1, 1));
        OlderThanAgeStrategy strategy = new OlderThanAgeStrategy(null);

        // when
        boolean result = strategy.isApplicable(user);

        // then
        assertFalse(result);
    }
}