package dev.prognitio.cacao.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;

import dev.prognitio.cacao.MiscellaneousFactsCurator;
import dev.prognitio.cacao.Notes;
import dev.prognitio.cacao.R;

public class FeedActivity extends AppCompatActivity {

    LinearLayout scrollarea;
    ScrollView scrollviewroot;


    ImageButton switchToCourseScreenButton;
    ImageButton switchToSettingsScreenButton;
    ImageButton switchToCalendarScreenButton;
    ImageButton switchToNotesScreenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Context context = getApplicationContext();





        scrollarea = findViewById(R.id.feedscrolllayout);
        scrollviewroot = findViewById(R.id.feedscrollarea);

        for (int i = 0; i < 5; i++) { //generate 5 tiles on startup, generate more once the user reaches the bottom
            LinearLayout layout = generateFeedTile(context);
            scrollarea.addView(layout);
        }

        scrollviewroot.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int x, int y, int oldX, int oldY) {
                if (!view.canScrollVertically(1)) {
                    //reached bottom of view, add layout to view
                    LinearLayout layout = generateFeedTile(context);
                    scrollarea.addView(layout);
                }
            }
        });


        switchToCourseScreenButton = findViewById(R.id.course_button);
        switchToCourseScreenButton.setOnClickListener(view -> {
            Intent switchActivityIntent = new Intent(context, CourseDisplayActivity.class);
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

    private LinearLayout generateFeedTile(Context context) {

        final float density = context.getResources().getDisplayMetrics().density;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins((int) (20 * density), (int) (20 * density), (int) (20 * density), (int) (20 * density));

        LinearLayout layout = new LinearLayout(context);
        layout.setPadding(0, (int) (50 * density), 0, (int) (50 * density));
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);

        //format tile
        layout.setBackground(AppCompatResources.getDrawable(context, R.drawable.rounded_button));
        layout.setBackgroundColor(getColor(R.color.secondary_background));


        Random random = new Random();
        Double randomN = random.nextDouble();


        SharedPreferences checkNotes = context.getSharedPreferences(getString(R.string.usernotes_key), Context.MODE_PRIVATE);
        boolean hasNotes = checkNotes.contains("noteregistry");


        if (randomN > 0.5 && hasNotes) {
            //show notes
            SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.usernotes_key), Context.MODE_PRIVATE);
            ArrayList<Notes> notesList = new ArrayList<>();
            String noteRegistry = sharedPref.getString("noteregistry", "");
            for (String noteTitle : noteRegistry.split(",")) {
                Notes newNote = Notes.fromString(sharedPref.getString("note_" + noteTitle, ""));
                if (newNote != null) {
                    notesList.add(newNote);
                }
            }
            if (!notesList.isEmpty()) {
                Notes note = Notes.notesCurator(notesList);
                System.out.println("NOTE: " + note);

                LinearLayout.LayoutParams headerlayoutparams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, (int) (50 * density)
                );

                LinearLayout headerLayout = new LinearLayout(context);
                headerLayout.setLayoutParams(headerlayoutparams);
                headerLayout.setOrientation(LinearLayout.HORIZONTAL);

                //format tile
                float[] hsv = new float[3];
                Color.colorToHSV(getColor(R.color.secondary_background), hsv);
                hsv[2] *= 0.8f;
                headerLayout.setBackgroundColor(Color.HSVToColor(hsv));
                TextView title = new TextView(context);title.setText(note.getTitle());
                title.setTextSize(22);
                //title.setGravity(Gravity.CENTER);
                title.setTextColor(getColor(R.color.text_color));
                title.setTypeface(Typeface.create("audiowide", Typeface.NORMAL));
                title.setPadding((int) (density * 10), 0, (int) (density * 10), (int) (density * 10));
                TextView content = new TextView(context);content.setText(note.getContent());
                content.setTextSize(18);
                //content.setGravity(Gravity.CENTER);
                content.setTextColor(getColor(R.color.text_color));
                content.setTypeface(Typeface.create("roboto_mono", Typeface.NORMAL));
                content.setPadding((int) (density * 10), 0, (int) (density * 10), 0);
                headerLayout.addView(title);
                layout.addView(headerLayout);
                layout.addView(content);
            }

        } else { //feed content pulled from api
            Executors.newSingleThreadExecutor().execute(() -> {

                HashMap<String, String> apiFactInfo = new HashMap<>();
                try {
                    SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.userpref_key), Context.MODE_PRIVATE);
                    apiFactInfo = MiscellaneousFactsCurator.curateFeedTile(context, sharedPref);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final HashMap<String, String> data = apiFactInfo;

                runOnUiThread(() -> {
                    System.out.println(data);

                    TextView title = new TextView(context);title.setText(data.get("title"));
                    title.setTextSize(22);
                    //title.setGravity(Gravity.CENTER);
                    title.setTextColor(getColor(R.color.text_color));
                    title.setTypeface(Typeface.create("audiowide", Typeface.NORMAL));
                    title.setPadding((int) (density * 10), 0, (int) (density * 10), (int) (density * 10));
                    TextView content = new TextView(context);content.setText(data.get("body"));
                    content.setTextSize(18);
                    //content.setGravity(Gravity.CENTER);
                    content.setTextColor(getColor(R.color.text_color));
                    content.setTypeface(Typeface.create("roboto_mono", Typeface.NORMAL));
                    content.setPadding((int) (density * 10), 0, (int) (density * 10), 0);
                    layout.addView(title);
                    layout.addView(content);
                });
            });
        }

        return layout;
    }
}