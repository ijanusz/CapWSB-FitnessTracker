package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MonthlyTrainingReportScheduler {

    private final TrainingProvider trainingProvider;
    private final UserProvider userProvider;
    private final EmailSender emailSender;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void sendMonthlyTrainingReports() {
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfCurrentMonth = now.withDayOfMonth(1);
        LocalDate firstDayOfPreviousMonth = firstDayOfCurrentMonth.minusMonths(1);
        LocalDate lastDayOfPreviousMonth = firstDayOfCurrentMonth.minusDays(1);

        Date startDate = Date.from(firstDayOfPreviousMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(lastDayOfPreviousMonth.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());

        List<User> users = userProvider.findAllUsers();

        for (User user : users) {
            List<Training> userTrainings = trainingProvider.findTrainingsByUserIdAndDateRange(user.getId(), startDate, endDate);

            String content = generateReportContent(user, userTrainings);

            EmailDto email = new EmailDto(
                    user.getEmail(),
                    "Your Monthly Training Report",
                    content
            );

            emailSender.send(email);
        }
    }

    private String generateReportContent(User user, List<Training> trainings) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hello ").append(user.getFirstName()).append(",\n\n");
        sb.append("Here's your training summary for the last month:\n\n");
        sb.append("Total Trainings: ").append(trainings.size()).append("\n\n");

        if (!trainings.isEmpty()) {
            sb.append("Details:\n");
            for (Training training : trainings) {
                sb.append("- ").append(training.getActivityType())
                        .append(" on ").append(training.getStartTime()).append("\n");
            }
            sb.append("\n");
        }

        sb.append("Keep up the great work!\n");
        sb.append("Best regards,\nYour Fitness Tracker Team");
        return sb.toString();
    }
}
