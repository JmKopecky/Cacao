package dev.prognitio.cacao.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import dev.prognitio.cacao.R;

public class Settings extends AppCompatActivity {

    ImageButton switchToCourseScreenButton;
    ImageButton switchToCalendarScreenButton;
    ImageButton switchToFeedScreenButton;
    ImageButton switchToNotesScreenButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.userinfo_key), Context.MODE_PRIVATE);

        String name = sharedPreferences.getString("usersupplied_name", "");
        EditText text_name = findViewById(R.id.name_text);
        text_name.setText(name);

        String username = sharedPreferences.getString("usersupplied_username", "");
        EditText text_username = findViewById(R.id.username_text);
        text_username.setText(username);

        String email = sharedPreferences.getString("usersupplied_email", "");
        EditText text_email = findViewById(R.id.email_text);
        text_email.setText(email);

        Float feed = sharedPreferences.getFloat("usersupplied_feed", 0.5f);
        EditText text_feed = findViewById(R.id.feed_text);
        text_feed.setText("" + feed);


        Button button_name = findViewById(R.id.name_button);
        button_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.userinfo_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                String name = text_name.getText().toString();
                editor.putString("usersupplied_name", name);
                editor.apply();
                text_name.setText("" + sharedPref.getString("usersupplied_name", "no name"));
            }
        });

        Button button_username = findViewById(R.id.username_button);
        button_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.userinfo_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                String name = text_username.getText().toString();
                editor.putString("usersupplied_username", name);
                editor.apply();
                text_username.setText("" + sharedPref.getString("usersupplied_username", "no username"));
            }
        });

        Button button_email = findViewById(R.id.email_button);
        button_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.userinfo_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                String name = text_email.getText().toString();
                editor.putString("usersupplied_email", name);
                editor.apply();
                text_email.setText("" + sharedPref.getString("usersupplied_email", "no email"));
            }
        });

        Button button_feed = findViewById(R.id.change_probability);
        button_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.userinfo_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                String name = text_feed.getText().toString();
                float asFloat;
                try {
                    asFloat = Float.parseFloat(name);
                    text_feed.setTextColor(getResources().getColor(R.color.text_color));
                } catch (NumberFormatException e) {
                    asFloat = 0.5f;
                    text_feed.setTextColor(getResources().getColor(R.color.red));
                }
                editor.putFloat("usersupplied_feed", asFloat);
                editor.apply();
                text_feed.setText("" + sharedPref.getFloat("usersupplied_feed", 0.5f));
            }
        });




        switchToFeedScreenButton = findViewById(R.id.home_button);
        switchToFeedScreenButton.setOnClickListener(view -> {
            Intent switchActivityIntent = new Intent(context, FeedActivity.class);
            startActivity(switchActivityIntent);
        });
        switchToCalendarScreenButton = findViewById(R.id.calendar);
        switchToCalendarScreenButton.setOnClickListener(view -> {
            Intent switchActivityIntent = new Intent(context, CalendarActivity.class);
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