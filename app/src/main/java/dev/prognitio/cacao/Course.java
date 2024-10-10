package dev.prognitio.cacao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Course implements Comparable<Course> {

    public String courseName;
    public String teacher;
    public int semester;
    public double GPA;
    public int grade;

    public Course(String courseName, @Nullable String teacher, int semester, double GPA, int grade) {

        if (teacher != null && teacher.contains("^")) {
            teacher = teacher.replace("^", ",");
        }

        this.courseName = courseName;
        this.teacher = teacher;
        this.semester = semester;
        this.GPA = GPA;
        this.grade = grade;
    }


    public double calculateGPA() {
        double output = -1;

        output = GPA - ((100 - grade) * 0.1);

        BigDecimal asBigDecimal = new BigDecimal(Double.toString(output));
        return Math.max(asBigDecimal.round(new MathContext(3, RoundingMode.HALF_UP)).doubleValue(), 0);
    }

    public String getSemesterAsString() {
        String output = "ERROR";
        output = semester % 2 == 0 ? "Spring" : "Fall";
        return output;
    }

    public String toString() {
        String result;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        result = gson.toJson(this);
        return result;
    }

    public static Course fromString(String str) {
        Course output;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        output = gson.fromJson(str, Course.class);
        return output;
    }

    @Override
    public int compareTo(Course o) {
        if (this.semester != o.semester) {
            return o.semester - this.semester; //semester in descending order
        } else {
            return 0; //do nothing to prior order
        }
    }
}
