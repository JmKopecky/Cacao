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

import dev.prognitio.cacao.Assignment;
import dev.prognitio.cacao.R;

public class CalendarActivity extends AppCompatActivity {

    ImageButton switchToCourseScreenButton;
    ImageButton switchToSettingsScreenButton;
    ImageButton switchToFeedScreenButton;
    ImageButton switchToNotesScreenButton;

    LinearLayout scrollarea;
    ScrollView scrollviewroot;

    ArrayList<String> months = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Context context = getApplicationContext();

        final float density = context.getResources().getDisplayMetrics().density;

        scrollarea = findViewById(R.id.calendarscrolllayout);
        scrollviewroot = findViewById(R.id.calendarscrollarea);

        months.add("January");months.add("February");months.add("March");
        months.add("April");months.add("May");months.add("June");
        months.add("July");months.add("August");months.add("September");
        months.add("October");months.add("November");months.add("December");

        //retrieve assignments
        ArrayList<Assignment> assignmentList = new ArrayList<Assignment>();
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.userassignments_key), Context.MODE_PRIVATE);
        int maxAssignmentIndex = sharedPref.getInt("assignment_maxindex", -1);

        if (maxAssignmentIndex > 0) {
            //there are some assignments
            for (int i = 1; i <= maxAssignmentIndex; i++) {
                Assignment assignment = Assignment.fromString(sharedPref.getString("assignment_" + i, ""));
                assignmentList.add(assignment);
            }
        }

        //display assignments
        for (String month : months) {

            ArrayList<Assignment> relevantAssignments = new ArrayList<>();
            for (Assignment assignment : assignmentList) {
                if (assignment.getMonth().equals(month)) {
                    relevantAssignments.add(assignment);
                }
            }

            TextView monthMarker = new TextView(context);monthMarker.setText(month);
            monthMarker.setTypeface(Typeface.create("audiowide", Typeface.NORMAL));
            monthMarker.setPadding((int) (density * 10), (int) (density * 15), (int) (density * 10), (int) (density * 15));
            if (relevantAssignments.isEmpty()) {
                monthMarker.setTextSize(14);
                monthMarker.setTextColor(getColor(R.color.secondary_background));
            } else {
                monthMarker.setTextSize(24);
                monthMarker.setTextColor(getColor(R.color.accent_color));
            }
            scrollarea.addView(monthMarker);
            
            

            for (Assignment assignment : relevantAssignments) {

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, (int) (100 * density)
                );
                params.setMargins((int) (10 * density), (int) (10 * density), (int) (10 * density), (int) (10 * density));
                LinearLayout layout = new LinearLayout(context);
                layout.setLayoutParams(params);
                layout.setOrientation(LinearLayout.VERTICAL);

                //format tile
                layout.setBackground(AppCompatResources.getDrawable(context, R.drawable.rounded_button));
                layout.setBackgroundColor(getColor(R.color.secondary_background));


                LinearLayout.LayoutParams paramsTopBar = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, (int) (50 * density)
                );
                LinearLayout topBar = new LinearLayout(context);
                topBar.setLayoutParams(paramsTopBar);
                topBar.setOrientation(LinearLayout.HORIZONTAL);

                TextView dueDate = new TextView(context);dueDate.setText(assignment.getDueDate());
                dueDate.setTextSize(18);
                dueDate.setTextColor(getColor(R.color.text_color));
                dueDate.setTypeface(Typeface.create("roboto_mono", Typeface.NORMAL));
                dueDate.setPadding((int) (density * 10), 0, (int) (density * 10), 0);
                TextView course = new TextView(context);course.setText(assignment.getApplicableCourse());
                course.setTextSize(18);
                course.setTextColor(getColor(R.color.text_color));
                course.setTypeface(Typeface.create("roboto_mono", Typeface.NORMAL));
                course.setPadding((int) (density * 10), 0, (int) (density * 10), 0);
                TextView title = new TextView(context);title.setText(assignment.getTitle());
                title.setTextSize(20);
                title.setTextColor(getColor(R.color.text_color));
                title.setTypeface(Typeface.create("audiowide", Typeface.NORMAL));
                title.setPadding((int) (density * 10), 0, (int) (density * 10), 0);
                topBar.addView(title);
                topBar.addView(course);
                topBar.addView(dueDate);

                TextView details = new TextView(context);details.setText(assignment.getDetails());
                details.setTextSize(16);
                details.setTextColor(getColor(R.color.text_color));
                details.setTypeface(Typeface.create("roboto_mono", Typeface.NORMAL));
                title.setPadding((int) (density * 10), 0, (int) (density * 10), 0);


                layout.addView(topBar);
                layout.addView(details);

                scrollarea.addView(layout);
            }
        }



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
        switchToCourseScreenButton = findViewById(R.id.course_button);
        switchToCourseScreenButton.setOnClickListener(view -> {
            Intent switchActivityIntent = new Intent(context, CourseDisplayActivity.class);
            startActivity(switchActivityIntent);
        });
        switchToNotesScreenButton = findViewById(R.id.add_notes);
        switchToNotesScreenButton.setOnClickListener(view -> {
            Intent switchActivityIntent = new Intent(context, NotesActivity.class);
            startActivity(switchActivityIntent);
        });

    }
}