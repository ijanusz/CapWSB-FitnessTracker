package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.IntegrationTest;
import com.capgemini.wsb.fitnesstracker.IntegrationTestBase;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@IntegrationTest
@Transactional
@AutoConfigureMockMvc(addFilters = false)
class MonthlyTrainingReportSchedulerTest extends IntegrationTestBase {

    @Autowired
    private MonthlyTrainingReportScheduler scheduler;

    @MockBean
    private EmailSender emailSender;

    @Test
    void testSendMonthlyTrainingReports() {
        User user1 = createUser("John", "Doe", "john.doe@example.com", LocalDate.of(1990, 1, 1));
        User user2 = createUser("Jane", "Smith", "jane.smith@example.com", LocalDate.of(1992, 2, 2));

        Training training1 = createAndPersistTraining(user1, ActivityType.RUNNING, 5.0, 10.0);

        scheduler.sendMonthlyTrainingReports();

        verifyEmailForUser(user1, 1);
        verifyEmailForUser(user2, 0);
    }

    private User createUser(String firstName, String lastName, String email, LocalDate birthDate) {
        return existingUser(new User(firstName, lastName, birthDate, email));
    }

    private Training createAndPersistTraining(User user, ActivityType activityType, double duration, double distance) {
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfPreviousMonth = now.withDayOfMonth(1).minusMonths(1);
        LocalDate lastDayOfPreviousMonth = now.withDayOfMonth(1).minusDays(1);

        Date startDate = Date.from(firstDayOfPreviousMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(lastDayOfPreviousMonth.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());

        Training training = new Training(user, startDate, endDate, activityType, duration, distance);
        persistTraining(training);
        return training;
    }

    private void verifyEmailForUser(User user, int expectedTrainingCount) {
        ArgumentCaptor<EmailDto> emailCaptor = ArgumentCaptor.forClass(EmailDto.class);
        verify(emailSender, atLeastOnce()).send(emailCaptor.capture());

        List<EmailDto> sentEmails = emailCaptor.getAllValues();
        EmailDto email = sentEmails.stream()
                .filter(e -> e.toAddress().equals(user.getEmail()))
                .findFirst()
                .orElse(null);

        assertNotNull(email, "User should receive an email");
        assertTrue(email.content().contains("Total Trainings: " + expectedTrainingCount),
                "Email content should indicate " + expectedTrainingCount + " training(s).");
    }
}