package dev.prognitio.cacao.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

import dev.prognitio.cacao.R;

public class CourseSetup extends AppCompatActivity {

    EditText courseNameInput;
    EditText courseSemesterInput;
    EditText courseGPAInput;
    EditText courseGradeInput;

    ImageButton importCourseDataButton;
    ImageButton finalizeCourseDataButton;
    ImageButton addCourseButton;

    //ArrayList<Courses> courses = new ArrayList<Courses>();

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
            String courseSemester = courseSemesterInput.getText().toString();
            String courseGPA = courseGPAInput.getText().toString();
            String courseGrade = courseGradeInput.getText().toString();

            courseNameInput.setText("");
            courseSemesterInput.setText("");
            courseGPAInput.setText("");
            courseGradeInput.setText("");

            //create a class object
        }));

        finalizeCourseDataButton.setOnClickListener(view -> {
            String courseName = courseNameInput.getText().toString();
            String courseSemester = courseSemesterInput.getText().toString();
            String courseGPA = courseGPAInput.getText().toString();
            String courseGrade = courseGradeInput.getText().toString();

            //create a class object

            //Intent switchActivityIntent = new Intent(context, CourseSetup.class);
            //startActivity(switchActivityIntent);
        });


    }
}