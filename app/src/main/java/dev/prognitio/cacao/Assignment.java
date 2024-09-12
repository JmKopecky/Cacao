package dev.prognitio.cacao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Assignment {

    String dueDate;



    String applicableCourse;
    String title;
    String details;

    public Assignment(@Nullable String applicableCourse, String dueDate, String title, @Nullable String details) {
        this.dueDate = dueDate;
        this.applicableCourse = applicableCourse;
        this.title = title;
        this.details = details;
    }

    public String getMonth() {
        switch (Integer.parseInt(dueDate.split("/")[1].split("/")[0])) {
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
        }
        return "DOOMSDAY";
    }


    public static void scheduleNotifications(Assignment assignment, Context context) {

        Instant instant1 = Instant.parse("2020-07-10T15:00:00Z");
        Instant instant2 = Instant.parse("2019-04-21T05:25:00Z");

        LocalDate due = assignment.getDateAsLocalDate();
        LocalDate now = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
        long between = ChronoUnit.HOURS.between(now, due);


        WorkRequest sendNotifWorker =
                new OneTimeWorkRequest.Builder(AssignmentNotifWorker.class)
                        .setInputData(
                                new Data.Builder().putString("assignment", assignment.toString()).build())
                        .setInitialDelay(between, TimeUnit.HOURS)
                        .build();

        WorkManager.getInstance(context).enqueue(sendNotifWorker);
    }


    public String toString() {
        String result;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        result = gson.toJson(this);
        return result;
    }

    public static Assignment fromString(String str) {
        Assignment output;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        output = gson.fromJson(str, Assignment.class);
        return output;
    }


    public String getDueDate() {
        return dueDate;
    }

    public LocalDate getDateAsLocalDate() {
        return LocalDate.of(Integer.parseInt(dueDate.split("/")[0]), Integer.parseInt(dueDate.split("/")[1]), Integer.parseInt(dueDate.split("/")[2]));
    }

    public String getApplicableCourse() {
        return applicableCourse;
    }

    public void setApplicableCourse(String applicableCourse) {
        this.applicableCourse = applicableCourse;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}

