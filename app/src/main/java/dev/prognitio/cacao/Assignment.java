package dev.prognitio.cacao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.Date;

public class Assignment {

    LocalDate dueDate;
    String applicableCourse;
    String title;
    String details;

    public Assignment(@Nullable String applicableCourse, LocalDate dueDate, String title, @Nullable String details) {
        this.dueDate = dueDate;
        this.applicableCourse = applicableCourse;
        this.title = title;
        this.details = details;
    }

    public LocalDate getDueDate() {
        return dueDate;
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
}
