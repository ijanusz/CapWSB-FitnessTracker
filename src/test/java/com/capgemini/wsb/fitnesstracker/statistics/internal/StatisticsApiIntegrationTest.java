package com.capgemini.wsb.fitnesstracker.statistics.internal;


import com.capgemini.wsb.fitnesstracker.IntegrationTest;
import com.capgemini.wsb.fitnesstracker.IntegrationTestBase;
import com.capgemini.wsb.fitnesstracker.statistics.api.model.Statistics;
import com.capgemini.wsb.fitnesstracker.user.api.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.time.LocalDate.now;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@Transactional
@AutoConfigureMockMvc(addFilters = false)
class StatisticsApiIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    private static User generateUser() {
        return new User(randomUUID().toString(), randomUUID().toString(), now().minusYears(25), randomUUID().toString());
    }

    private static Statistics generateStatistics(User user) {
        return new Statistics(user, 10, 200.5, 5000);
    }

    @Test
    void shouldCreateStatistics_whenPostingNewStatistics() throws Exception {
        User user = existingUser(generateUser());

        String requestBody = """
                {
                    "userId": "%s",
                    "distance": 200.5,
                    "calories": 5000
                }
                """.formatted(user.getId());

        mockMvc.perform(post("/v1/statistics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(log())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.totalTrainings").value(1))
                .andExpect(jsonPath("$.totalDistance").value(200.5))
                .andExpect(jsonPath("$.totalCaloriesBurned").value(5000));
    }

    @Test
    void shouldUpdateStatistics_whenPuttingUpdatedStatistics() throws Exception {
        User user = existingUser(generateUser());
        Statistics statistics = persistStatistics(generateStatistics(user));

        String requestBody = """
                {
                    "totalTrainings": 20,
                    "totalDistance": 300.5,
                    "totalCaloriesBurned": 7000
                }
                """;

        mockMvc.perform(put("/v1/statistics/{statisticsId}", statistics.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.totalTrainings").value(20))
                .andExpect(jsonPath("$.totalDistance").value(300.5))
                .andExpect(jsonPath("$.totalCaloriesBurned").value(7000));
    }

    @Test
    void shouldReturnStatisticsForUser_whenGettingStatisticsByUserId() throws Exception {
        User user = existingUser(generateUser());
        Statistics statistics = persistStatistics(generateStatistics(user));

        mockMvc.perform(get("/v1/statistics/{userId}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.totalTrainings").value(statistics.getTotalTrainings()))
                .andExpect(jsonPath("$.totalDistance").value(statistics.getTotalDistance()))
                .andExpect(jsonPath("$.totalCaloriesBurned").value(statistics.getTotalCaloriesBurned()));
    }

    @Test
    void shouldDeleteStatistics_whenDeletingStatisticsById() throws Exception {
        User user = existingUser(generateUser());
        Statistics statistics = persistStatistics(generateStatistics(user));

        mockMvc.perform(delete("/v1/statistics/{statisticsId}", statistics.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isNoContent());

        List<Statistics> allStatistics = getAllStatistics();
        assertThat(allStatistics).isEmpty();
    }

    @Test
    void shouldFindStatisticsByCaloriesGreaterThan_whenGettingByCalories() throws Exception {
        User user1 = existingUser(generateUser());
        User user2 = existingUser(generateUser());

        Statistics stats1 = persistStatistics(new Statistics(user1, 5, 150.0, 4000));
        Statistics stats2 = persistStatistics(new Statistics(user2, 10, 300.0, 7000));

        mockMvc.perform(get("/v1/statistics/search/calories")
                        .param("calories", "5000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userId").value(user2.getId()))
                .andExpect(jsonPath("$[0].totalCaloriesBurned").value(7000));
    }
}