package dev.prognitio.cacao.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;


import java.util.HashMap;
import java.util.Random;

import dev.prognitio.cacao.MiscellaneousFactsCurator;
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
                LinearLayout.LayoutParams.MATCH_PARENT, (int) (400 * density)
        );

        params.setMargins((int) (48 * density), (int) (48 * density), (int) (48 * density), (int) (48 * density));

        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);

        //format tile
        layout.setBackground(AppCompatResources.getDrawable(context, R.drawable.rounded_button));
        layout.setBackgroundColor(getColor(R.color.secondary_background));


        Random random = new Random();
        Double randomN = random.nextDouble();

        if (randomN > 0.5) {
            //show notes

        } else {
            //show content from selected topics
            HashMap<String, String> apiFactInfo = MiscellaneousFactsCurator.curateFeedTile(context);
        }

        return layout;
    }
}