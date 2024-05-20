package dev.prognitio.cacao.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import dev.prognitio.cacao.R;


public class PreferencesSelection extends AppCompatActivity {

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences_selection);
        Context context = getApplicationContext();


        layout = findViewById(R.id.scrolllayout);


        for (int i = 0; i < 40; i++) {
            CheckedTextView toAdd = new CheckedTextView(context);
            toAdd.setText("" + i);
            toAdd.setTextSize(20);
            toAdd.setTextColor(getColor(R.color.text_color));
            toAdd.setTypeface(Typeface.create("audiowide", Typeface.NORMAL));
            layout.addView(toAdd);
        }
    }
}