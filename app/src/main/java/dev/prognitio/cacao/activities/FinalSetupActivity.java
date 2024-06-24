package dev.prognitio.cacao.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dev.prognitio.cacao.R;
import dev.prognitio.cacao.activities.ProfileLaunch;
import dev.prognitio.cacao.log.LogType;
import dev.prognitio.cacao.log.Logger;

public class FinalSetupActivity extends AppCompatActivity {
    Button proceedButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_setup);

        Context context = getApplicationContext();

        proceedButton = findViewById(R.id.finishSetupButton);

        proceedButton.setOnClickListener(view -> {
            Logger.log("FinalSetupActivity Button Activated", LogType.DEBUG, null);

            SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.usercourses_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putBoolean("hasfinishedsetup", true);

            editor.apply();

            Intent switchActivityIntent = new Intent(context, FeedActivity.class);
            startActivity(switchActivityIntent);
        });
    }
}