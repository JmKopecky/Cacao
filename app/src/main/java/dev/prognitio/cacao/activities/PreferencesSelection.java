package dev.prognitio.cacao.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import dev.prognitio.cacao.R;
import dev.prognitio.cacao.log.LogType;
import dev.prognitio.cacao.log.Logger;


public class PreferencesSelection extends AppCompatActivity {


    public static ArrayList<String> options = new ArrayList<>();

    LinearLayout layout;
    Button proceedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences_selection);
        Context context = getApplicationContext();

        populateOptions();

        layout = findViewById(R.id.scrolllayout);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layout.getLayoutParams();
        params.setMargins(0, 10, 0, 0);
        proceedButton = findViewById(R.id.submitpreferencesbutton);


        for (String feedOption : options) {
            CheckedTextView toAdd = new CheckedTextView(context);
            toAdd.setText(feedOption);
            toAdd.setTextSize(20);
            toAdd.setTextColor(getColor(R.color.text_color));
            toAdd.setTypeface(Typeface.create("audiowide", Typeface.NORMAL));
            toAdd.setPadding(0,10,0,10);
            toAdd.setWidth(params.width);
            toAdd.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            toAdd.setChecked(false);
            toAdd.setOnClickListener(view -> {
                if (((CheckedTextView) view).isChecked()) {
                    ((CheckedTextView) view).setChecked(false);
                    ((CheckedTextView) view).setTextColor(getColor(R.color.text_color));
                } else {
                    ((CheckedTextView) view).setChecked(true);
                    ((CheckedTextView) view).setTextColor(getColor(R.color.accent_color));
                }
            });
            layout.addView(toAdd);
        }

        proceedButton.setOnClickListener(view -> {
            Logger.log("SubmitPreferences Button Activated", LogType.DEBUG, null);

            //store preferences data
            SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.userpref_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            for (int i = 1; i < layout.getChildCount(); i++) {
                CheckedTextView element = (CheckedTextView) layout.getChildAt(i);
                String key = "preferences_" + element.getText().toString();
                Logger.log(key + " " + element.isChecked(), LogType.DEBUG, null);
                editor.putBoolean(key, element.isChecked());
            }

            editor.apply();

            //switch activity
            Intent switchActivityIntent = new Intent(context, CourseSetup.class);
            startActivity(switchActivityIntent);
        });
    }


    private static void populateOptions() {
        options.add("Assorted Definitions");
        options.add("Random Facts 1");
        options.add("Random Facts 2");
        options.add("Random Facts 3");
        options.add("Random Facts 4");
        options.add("Random Facts 5");
        options.add("Random Facts 6");
        options.add("Random Facts 7");
        options.add("Random Facts 8");
        options.add("Random Facts 9");
        options.add("Random Facts 10");
        options.add("Random Facts 11");
        options.add("Random Facts 12");
        options.add("Random Facts 13");
    }
}