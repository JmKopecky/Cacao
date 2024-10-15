package dev.prognitio.cacao.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

    ImageButton finalizeCourseDataButton;
    ImageButton addCourseButton;

    ArrayList<Course> courses = new ArrayList<Course>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_setup);
        Context context = getApplicationContext();

        Bundle extras = getIntent().getExtras();

        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.usercourses_key), Context.MODE_PRIVATE);
        int maxId = sharedPref.getInt("course_maxid", -1);
        if (maxId > -1) {
            for (int i = 1; i <= maxId; i++) {
                String toAdd = sharedPref.getString("course_" + i, "");
                if (!toAdd.isEmpty()) {
                    courses.add(Course.fromString(toAdd));
                }
            }
        }


        courseNameInput = findViewById(R.id.inputClassName);
        courseSemesterInput = findViewById(R.id.inputSemesterNumber);
        courseGPAInput = findViewById(R.id.inputGPAWeight);
        courseGradeInput = findViewById(R.id.inputGrade);

        finalizeCourseDataButton = findViewById(R.id.finalizeCoursesButton);
        addCourseButton = findViewById(R.id.addCourseButton);

        if (extras != null) {
            String toEdit = extras.getString("edit_target");
            if (toEdit != null && toEdit.contains("course")) {
                System.out.println(toEdit);
                Course course = Course.fromString(toEdit);
                int cid = -1;
                for (int j = 1; j <= maxId; j++) {
                    Course possible = Course.fromString(sharedPref.getString("course_" + j, ""));
                    if (course.courseName.equals(possible.courseName) && course.semester == possible.semester) {
                        cid = j;
                        break;
                    }
                }
                final int target = cid;

                courseNameInput.setText(course.courseName);
                courseSemesterInput.setText("" + course.semester);
                courseGPAInput.setText("" + course.GPA);
                courseGradeInput.setText("" + course.grade);
                finalizeCourseDataButton.setVisibility(View.GONE);
                addCourseButton.setVisibility(View.GONE);

                Button deleteButton = findViewById(R.id.deleteButton);
                deleteButton.setVisibility(View.VISIBLE);
                deleteButton.setOnClickListener(view -> {
                    System.out.println(target);
                    System.out.println(sharedPref.getString("course" + target, "oops"));
                    SharedPreferences.Editor delete = sharedPref.edit();
                    delete.remove("course_" + target);
                    for (int i = target; i < maxId; i++) { //shift all later elements forward
                        delete.putString("course_" + i, sharedPref.getString("course_" + (i + 1), ""));
                    }
                    delete.remove("course_" + maxId);
                    delete.putInt("course_maxid", maxId - 1);
                    delete.apply();
                    Intent switchActivityIntent = new Intent(context, CourseDisplayActivity.class);
                    startActivity(switchActivityIntent);

                });

                Button finishButton = findViewById(R.id.finishSetupButton);
                finishButton.setText("Edit");
                finishButton.setOnClickListener(view -> {
                    String courseName = courseNameInput.getText().toString();
                    int courseSemester = -1;
                    double courseGPA = -1;
                    int courseGrade = -1;
                    boolean noFormattingErrors = true;
                    int targetId = target;


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
                        courseGrade = Integer.parseInt(courseGradeInput.getText().toString());
                    } catch (Exception e) {
                        courseGradeInput.setTextColor(getResources().getColor(R.color.red));
                        noFormattingErrors = false;
                    }

                    if (noFormattingErrors) {
                        Course toAdd = new Course(courseName, null, courseSemester, courseGPA, courseGrade);

                        SharedPreferences.Editor editor = sharedPref.edit();

                        String asString = toAdd.toString();
                        editor.putString("course_" + targetId, asString);

                        editor.apply();

                        Intent switchActivityIntent = new Intent(context, CourseDisplayActivity.class);
                        startActivity(switchActivityIntent);
                    }
                });
            }
            boolean shouldOverrideFinishSetupButton = extras.getBoolean("override_button");

            if (shouldOverrideFinishSetupButton) {
                findViewById(R.id.finishSetupButton).setVisibility(View.GONE);
                finalizeCourseDataButton.setOnClickListener(view -> {
                    String courseName = courseNameInput.getText().toString();
                    int courseSemester = -1;
                    double courseGPA = -1;
                    int courseGrade = -1;
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
                        courseGrade = Integer.parseInt(courseGradeInput.getText().toString());
                    } catch (Exception e) {
                        courseGradeInput.setTextColor(getResources().getColor(R.color.red));
                        noFormattingErrors = false;
                    }

                    if (noFormattingErrors) {
                        Course course = new Course(courseName, null, courseSemester, courseGPA, courseGrade);
                        courses.add(course);

                        SharedPreferences.Editor editor = sharedPref.edit();

                        int id = 0;
                        for (Course c : courses) {
                            id++;
                            //save the course string
                            String asString = c.toString();
                            editor.putString("course_" + id, asString);
                        }
                        editor.putInt("course_maxid", id);

                        editor.apply();

                        Intent switchActivityIntent = new Intent(context, CourseDisplayActivity.class);
                        startActivity(switchActivityIntent);
                    }
                });
            }
        } else {
            finalizeCourseDataButton.setOnClickListener(view -> {
                String courseName = courseNameInput.getText().toString();
                int courseSemester = -1;
                double courseGPA = -1;
                int courseGrade = -1;
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
                    courseGrade = Integer.parseInt(courseGradeInput.getText().toString());
                } catch (Exception e) {
                    courseGradeInput.setTextColor(getResources().getColor(R.color.red));
                    noFormattingErrors = false;
                }

                if (noFormattingErrors) {
                    Course course = new Course(courseName, null, courseSemester, courseGPA, courseGrade);
                    courses.add(course);

                    SharedPreferences.Editor editor = sharedPref.edit();

                    int id = 0;
                    for (Course c : courses) {
                        id++;
                        //save the course string
                        String asString = c.toString();
                        editor.putString("course_" + id, asString);
                    }
                    editor.putInt("course_maxid", id);

                    editor.apply();

                    Intent switchActivityIntent = new Intent(context, FinalSetupActivity.class);
                    startActivity(switchActivityIntent);
                }
            });
        }


        addCourseButton.setOnClickListener((view -> {
            String courseName = courseNameInput.getText().toString();
            int courseSemester = -1;
            double courseGPA = -1;
            int courseGrade = -1;
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
                courseGrade = Integer.parseInt(courseGradeInput.getText().toString());
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

                Course course = new Course(courseName, null, courseSemester, courseGPA, courseGrade);
                courses.add(course);
            }
        }));
    }
}