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
import java.util.Arrays;

import dev.prognitio.cacao.Notes;
import dev.prognitio.cacao.R;

public class NotesActivity extends AppCompatActivity {


    ImageButton switchToCourseScreenButton;
    ImageButton switchToSettingsScreenButton;
    ImageButton switchToFeedScreenButton;
    ImageButton switchToCalendarScreenButton;

    ImageButton addNoteButton;

    LinearLayout scrollarea;
    ScrollView scrollviewroot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Context context = getApplicationContext();

        addNoteButton = findViewById(R.id.addnotebutton);
        scrollarea = findViewById(R.id.notesscrolllayout);
        scrollviewroot = findViewById(R.id.notesscrollarea);

        final float density = context.getResources().getDisplayMetrics().density;

        //code to display all present notes
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.usernotes_key), Context.MODE_PRIVATE);
        String noteRegistry = sharedPref.getString("noteregistry", "");
        if (!noteRegistry.equals("")) {
            ArrayList<String> notesStrings = new ArrayList<>();
            ArrayList<Notes> notes = new ArrayList<>();
            notesStrings.addAll(Arrays.asList(noteRegistry.split(",")));
            System.out.println(notesStrings);
            System.out.println(noteRegistry);

            for (String noteString : notesStrings) {
                notes.add(Notes.fromString(sharedPref.getString("note_" + noteString, "")));
                System.out.println("NoteStringResolver: " + sharedPref.getString("note_" + noteString, ""));
            }

            for (Notes note : notes) {
                //add note to screen
                System.out.println("NoteCreatorLoop: " + note.toString());
                createNoteTile(note, scrollarea, density, context);
            }
        } else {
            System.out.println("No notes detected.");
        }


        addNoteButton.setOnClickListener(view -> {
            Intent switchActivityIntent = new Intent(context, EditNoteActivity.class);
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
        switchToCourseScreenButton = findViewById(R.id.course_button);
        switchToCourseScreenButton.setOnClickListener(view -> {
            Intent switchActivityIntent = new Intent(context, CourseDisplayActivity.class);
            startActivity(switchActivityIntent);
        });
        switchToCalendarScreenButton = findViewById(R.id.calendar);
        switchToCalendarScreenButton.setOnClickListener(view -> {
            Intent switchActivityIntent = new Intent(context, CalendarActivity.class);
            startActivity(switchActivityIntent);
        });
    }


    private void createNoteTile(Notes note, LinearLayout scrollarea, float density, Context context) {
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

        TextView title = new TextView(context);title.setText(note.getTitle());
        title.setTextSize(22);
        title.setTextColor(getColor(R.color.text_color));
        title.setTypeface(Typeface.create("audiowide", Typeface.NORMAL));
        title.setPadding((int) (density * 10), 0, (int) (density * 10), 0);
        layout.addView(title);

        TextView content = new TextView(context);content.setText(note.getContent());
        content.setTextSize(18);
        content.setTextColor(getColor(R.color.text_color));
        content.setTypeface(Typeface.create("roboto_mono", Typeface.NORMAL));
        content.setPadding((int) (density * 10), 0, (int) (density * 10), 0);
        layout.addView(content);

        TextView weight = new TextView(context);weight.setText("Weight: " + note.getWeight());
        weight.setTextSize(14);
        weight.setTextColor(getColor(R.color.text_color));
        weight.setTypeface(Typeface.create("roboto_mono", Typeface.NORMAL));
        weight.setPadding((int) (density * 10), 0, (int) (density * 10), 0);
        layout.addView(weight);

        scrollarea.addView(layout);
    }

}