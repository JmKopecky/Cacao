package dev.prognitio.cacao.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import dev.prognitio.cacao.Course;
import dev.prognitio.cacao.R;

public class CourseDisplayActivity extends AppCompatActivity {


    LinearLayout scrollarea;
    ScrollView scrollviewroot;

    ImageButton switchToFeedScreenButton;
    ImageButton switchToSettingsScreenButton;
    ImageButton switchToCalendarScreenButton;
    ImageButton switchToNotesScreenButton;

    ImageButton addAssignmentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_display);
        Context context = getApplicationContext();

        scrollarea = findViewById(R.id.coursescrolllayout);
        scrollviewroot = findViewById(R.id.coursescrollarea);


        ArrayList<Course> courses = new ArrayList<Course>();
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.usercourses_key), Context.MODE_PRIVATE);
        int maxId = sharedPref.getInt("course_maxid", -1);

        for (int i = 1; i <= maxId; i++) {
            courses.add(Course.fromString(sharedPref.getString("course_" + i, "")));
        }


        float density = context.getResources().getDisplayMetrics().density; //get pixel density for properly sizing added elements

        for (Course course : courses) {

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins((int) (24 * density), (int) (8 * density), (int) (24 * density), (int) (8 * density));

            LinearLayout layout = new LinearLayout(context);
            layout.setLayoutParams(params);
            layout.setOrientation(LinearLayout.VERTICAL);

            //format tile
            layout.setBackground(AppCompatResources.getDrawable(context, R.drawable.rounded_button));
            layout.setBackgroundColor(getColor(R.color.secondary_background));

            //add things to the tile
            TextView courseName = new TextView(context);
            courseName.setText(course.courseName + " | Teacher = " + course.teacher);
            courseName.setTextSize(20);
            courseName.setTextColor(getColor(R.color.text_color));
            courseName.setTypeface(Typeface.create("audiowide", Typeface.NORMAL));
            courseName.setPadding(10, 10, 10, 10);
            layout.addView(courseName);

            TextView gradeInfo = new TextView(context);
            gradeInfo.setText("Grade = " + course.grade + " | GPA = " + course.calculateGPA() + "/" + course.GPA);
            gradeInfo.setTextSize(16);
            gradeInfo.setTextColor(getColor(R.color.text_color));
            gradeInfo.setTypeface(Typeface.create("roboto_mono", Typeface.NORMAL));
            gradeInfo.setPadding(10, 10, 10, 10);
            layout.addView(gradeInfo);

            TextView semester = new TextView(context);
            semester.setText("Semester = " + course.getSemesterAsString());
            semester.setTextSize(16);
            semester.setTextColor(getColor(R.color.text_color));
            semester.setTypeface(Typeface.create("roboto_mono", Typeface.NORMAL));
            semester.setPadding(10, 10, 10, 10);
            layout.addView(semester);

            scrollarea.addView(layout);
        }


        addAssignmentButton = findViewById(R.id.addassignmentbutton);
        addAssignmentButton.setOnClickListener(view -> {
            Intent switchActivityIntent = new Intent(context, AddAssignmentActivity.class);
            startActivity(switchActivityIntent);
        });



        switchToFeedScreenButton = findViewById(R.id.home_button);
        switchToFeedScreenButton.setOnClickListener(view -> {
            Intent switchActivityIntent = new Intent(context, FeedActivity.class);
            startActivity(switchActivityIntent);
        });
        switchToSettingsScreenButton = findViewById(R.id.settings);
        switchToSettingsScreenButton.setOnClickListener(view -> {
            Intent switchActivityIntent = new Intent(context, Settings.class);
            startActivity(switchActivityIntent);
        });
        switchToCalendarScreenButton = findViewById(R.id.calendar);
        switchToCalendarScreenButton.setOnClickListener(view -> {
            Intent switchActivityIntent = new Intent(context, CalendarActivity.class);
            startActivity(switchActivityIntent);
        });
        switchToNotesScreenButton = findViewById(R.id.add_notes);
        switchToNotesScreenButton.setOnClickListener(view -> {
            Intent switchActivityIntent = new Intent(context, NotesActivity.class);
            startActivity(switchActivityIntent);
        });
    }
}