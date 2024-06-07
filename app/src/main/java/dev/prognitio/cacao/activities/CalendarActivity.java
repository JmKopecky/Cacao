package dev.prognitio.cacao.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import dev.prognitio.cacao.R;

public class CalendarActivity extends AppCompatActivity {

    ImageButton switchToCourseScreenButton;
    ImageButton switchToSettingsScreenButton;
    ImageButton switchToFeedScreenButton;
    ImageButton switchToNotesScreenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Context context = getApplicationContext();








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