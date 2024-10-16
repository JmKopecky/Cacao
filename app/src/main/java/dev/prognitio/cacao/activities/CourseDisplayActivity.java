package dev.prognitio.cacao.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

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
        int maxId = Integer.parseInt(String.valueOf(sharedPref.getInt("course_maxid", -1)));

        for (int i = 1; i <= maxId; i++) {
            String toAdd = sharedPref.getString("course_" + i, "");
            if (!toAdd.isEmpty()) {
                courses.add(Course.fromString(toAdd));
            }
        }

        Collections.sort(courses);

        double gpa = 0;
        for (Course c : courses) {
            gpa += c.calculateGPA();
        }
        gpa /= courses.size();
        BigDecimal asBigDecimal = new BigDecimal(Double.toString(gpa));
        TextView gpaText = findViewById(R.id.gpa_text);gpaText.setText("GPA: " + asBigDecimal.round(new MathContext(4, RoundingMode.HALF_UP)).doubleValue());

        Button addCourseButton = findViewById(R.id.editcoursedatabutton);
        addCourseButton.setOnClickListener(view -> {
            Intent switchActivityIntent = new Intent(context, CourseSetup.class);
            switchActivityIntent.putExtra("edit_target", "none");
            switchActivityIntent.putExtra("override_button", true);
            startActivity(switchActivityIntent);
        });


        float density = context.getResources().getDisplayMetrics().density; //get pixel density for properly sizing added elements

        for (Course course : courses) {
            LinearLayout container = new LinearLayout(this);
            LinearLayout.LayoutParams containerLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (100 * density));
            container.setLayoutParams(containerLayout);
            ViewGroup.LayoutParams wrapContentParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            container.setOrientation(LinearLayout.HORIZONTAL);

            containerLayout.setMargins((int) (24 * density), (int) (8 * density), (int) (24 * density), (int) (8 * density));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins((int) (2 * density), (int) (2 * density), (int) (2 * density), (int) (2 * density));


            LinearLayout layout = new LinearLayout(context);
            layout.setId(ViewGroup.generateViewId());
            layout.setLayoutParams(params);
            layout.setOrientation(LinearLayout.VERTICAL);

            //format tile
            container.setBackground(AppCompatResources.getDrawable(context, R.drawable.rounded_button));
            container.setBackgroundColor(getColor(R.color.secondary_background));
            container.setId(ViewGroup.generateViewId());

            //add things to the tile
            TextView courseName = new TextView(context);
            courseName.setText(course.courseName);
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


            ImageButton editButton = new ImageButton(context);editButton.setImageResource(R.drawable.baseline_edit_24);
            editButton.setAdjustViewBounds(true);
            editButton.setId(ViewGroup.generateViewId());
            editButton.setLayoutParams(wrapContentParams);
            editButton.setBackgroundColor(getColor(R.color.transparent));
            editButton.setOnClickListener(view -> {
                Intent switchActivityIntent = new Intent(context, CourseSetup.class);
                switchActivityIntent.putExtra("edit_target", course.toString()); //todo this index does not match the stored index, resulting in problems with deletion.
                startActivity(switchActivityIntent);
            });

            container.addView(editButton);

            layout.setId(ViewGroup.generateViewId());


            container.addView(layout);


            scrollarea.addView(container);
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