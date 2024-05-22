package dev.prognitio.cacao.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

import dev.prognitio.cacao.Course;
import dev.prognitio.cacao.R;

public class CourseSetup extends AppCompatActivity {

    EditText courseNameInput;
    EditText courseSemesterInput;
    EditText courseGPAInput;
    EditText courseGradeInput;

    ImageButton importCourseDataButton;
    ImageButton finalizeCourseDataButton;
    ImageButton addCourseButton;

    ArrayList<Course> courses = new ArrayList<Course>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_setup);
        Context context = getApplicationContext();

        courseNameInput = findViewById(R.id.inputClassName);
        courseSemesterInput = findViewById(R.id.inputSemesterNumber);
        courseGPAInput = findViewById(R.id.inputGPAWeight);
        courseGradeInput = findViewById(R.id.inputGrade);

        importCourseDataButton = findViewById(R.id.importCourseDataButton);
        finalizeCourseDataButton = findViewById(R.id.finalizeCoursesButton);
        addCourseButton = findViewById(R.id.addCourseButton);


        addCourseButton.setOnClickListener((view -> {
            String courseName = courseNameInput.getText().toString();
            int courseSemester = -1;
            double courseGPA = -1;
            double courseGrade = -1;
            boolean noFormattingErrors = true;


            try {
                courseSemester = Integer.parseInt(courseSemesterInput.getText().toString());
            } catch (Exception e) {
                courseSemesterInput.setTextColor(getResources().getColor(R.color.red));
                noFormattingErrors = false;
            }
            try {
                courseGPA = Double.parseDouble(courseGPAInput.getText().toString());
            } catch (Exception e) {
                courseGPAInput.setTextColor(getResources().getColor(R.color.red));
                noFormattingErrors = false;
            }
            try {
                courseGrade = Double.parseDouble(courseGradeInput.getText().toString());
            } catch (Exception e) {
                courseGradeInput.setTextColor(getResources().getColor(R.color.red));
                noFormattingErrors = false;
            }

            if (noFormattingErrors) {
                courseNameInput.setText("");
                courseSemesterInput.setText("");
                courseGPAInput.setText("");
                courseGradeInput.setText("");
                courseSemesterInput.setTextColor(getResources().getColor(R.color.text_color));
                courseGradeInput.setTextColor(getResources().getColor(R.color.text_color));
                courseGPAInput.setTextColor(getResources().getColor(R.color.text_color));

                Course course = new Course(courseName, courseSemester, courseGPA, courseGrade);
                courses.add(course);
            }
        }));


        finalizeCourseDataButton.setOnClickListener(view -> {
            String courseName = courseNameInput.getText().toString();
            int courseSemester = -1;
            double courseGPA = -1;
            double courseGrade = -1;
            boolean noFormattingErrors = true;


            try {
                courseSemester = Integer.parseInt(courseSemesterInput.getText().toString());
            } catch (Exception e) {
                courseSemesterInput.setTextColor(getResources().getColor(R.color.red));
                noFormattingErrors = false;
            }
            try {
                courseGPA = Double.parseDouble(courseGPAInput.getText().toString());
            } catch (Exception e) {
                courseGPAInput.setTextColor(getResources().getColor(R.color.red));
                noFormattingErrors = false;
            }
            try {
                courseGrade = Double.parseDouble(courseGradeInput.getText().toString());
            } catch (Exception e) {
                courseGradeInput.setTextColor(getResources().getColor(R.color.red));
                noFormattingErrors = false;
            }

            if (noFormattingErrors) {
                Course course = new Course(courseName, courseSemester, courseGPA, courseGrade);
                courses.add(course);

                for (Course c : courses) {
                    System.out.println(c.toString());
                }

                //Intent switchActivityIntent = new Intent(context, CourseSetup.class);
                //startActivity(switchActivityIntent);
            }
        });


    }
}