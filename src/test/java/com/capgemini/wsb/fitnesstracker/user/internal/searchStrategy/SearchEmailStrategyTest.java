package com.capgemini.wsb.fitnesstracker.user.internal.searchStrategy;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchEmailStrategyTest {

    @Test
    void shouldReturnTrueWhenEmailMatches() {
        // given
        User user = new User(randomUUID().toString(), randomUUID().toString(), LocalDate.now(), randomUUID().toString());
        user.setEmail("test@example.com");
        SearchEmailStrategy strategy = new SearchEmailStrategy("test");

        // when
        boolean result = strategy.filter(user);

        // then
        assertTrue(result);
    }

    @Test
    void shouldReturnTrueWhenEmailContainsSubstringIgnoringCase() {
        // given
        User user = new User(randomUUID().toString(), randomUUID().toString(), LocalDate.now(), randomUUID().toString());
        user.setEmail("test@example.com");
        SearchEmailStrategy strategy = new SearchEmailStrategy("EXAMPLE");

        // when
        boolean result = strategy.filter(user);

        // then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenEmailDoesNotContainSubstring() {
        // given
        User user = new User(randomUUID().toString(), randomUUID().toString(), LocalDate.now(), randomUUID().toString());
        user.setEmail("test@example.com");
        SearchEmailStrategy strategy = new SearchEmailStrategy("nonexistent");

        // when
        boolean result = strategy.filter(user);

        // then
        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenEmailFilterIsNull() {
        // given
        User user = new User(randomUUID().toString(), randomUUID().toString(), LocalDate.now(), randomUUID().toString());
        user.setEmail("test@example.com");
        SearchEmailStrategy strategy = new SearchEmailStrategy(null);

        // when
        boolean result = strategy.filter(user);

        // then
        assertTrue(result);
    }

    @Test
    void shouldBeApplicableWhenUserAndEmailAreNotNull() {
        // given
        User user = new User(randomUUID().toString(), randomUUID().toString(), LocalDate.now(), randomUUID().toString());
        user.setEmail("test@example.com");
        SearchEmailStrategy strategy = new SearchEmailStrategy("test");

        // when
        boolean result = strategy.isApplicable(user);

        // then
        assertTrue(result);
    }

    @Test
    void shouldNotBeApplicableWhenUserIsNull() {
        // given
        SearchEmailStrategy strategy = new SearchEmailStrategy("test");

        // when
        boolean result = strategy.isApplicable(null);

        // then
        assertFalse(result);
    }

    @Test
    void shouldNotBeApplicableWhenEmailIsNull() {
        // given
        User user = new User(randomUUID().toString(), randomUUID().toString(), LocalDate.now(), randomUUID().toString());
        user.setEmail("test@example.com");
        SearchEmailStrategy strategy = new SearchEmailStrategy(null);

        // when
        boolean result = strategy.isApplicable(user);

        // then
        assertFalse(result);
    }
}