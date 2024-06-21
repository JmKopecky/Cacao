package dev.prognitio.cacao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.Date;

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
