package dev.prognitio.cacao.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.Map;

import dev.prognitio.cacao.MiscellaneousFactsCurator;
import dev.prognitio.cacao.R;
import dev.prognitio.cacao.log.LogType;
import dev.prognitio.cacao.log.Logger;


public class PreferencesSelection extends AppCompatActivity {


    LinearLayout layout;
    Button proceedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences_selection);
        Context context = getApplicationContext();

        layout = findViewById(R.id.preferencesscrolllayout);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layout.getLayoutParams();
        params.setMargins(0, 10, 0, 0);
        proceedButton = findViewById(R.id.finishSetupButton);

        MiscellaneousFactsCurator.populateFactOption();
        for (Map.Entry<String,String> entry : MiscellaneousFactsCurator.factOption.entrySet()) {
            String feedOption = entry.getKey();
            CheckedTextView toAdd = new CheckedTextView(context);
            toAdd.setText(feedOption);
            toAdd.setTextSize(20);
            toAdd.setTextColor(getColor(R.color.text_color));
            toAdd.setTypeface(Typeface.create("audiowide", Typeface.NORMAL));
            toAdd.setPadding(0, 10, 0, 10);
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

            for (int i = 0; i < layout.getChildCount(); i++) {
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
}