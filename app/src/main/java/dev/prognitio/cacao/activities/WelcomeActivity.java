package dev.prognitio.cacao.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dev.prognitio.cacao.R;
import dev.prognitio.cacao.log.LogType;
import dev.prognitio.cacao.log.Logger;

public class WelcomeActivity extends AppCompatActivity {

    Button proceedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Context context = getApplicationContext();

        proceedButton = findViewById(R.id.launchSetupButton);

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.log("WelcomeActivity Button Activated", LogType.DEBUG, null);
                Intent switchActivityIntent = new Intent(context, ProfileLaunch.class);
                startActivity(switchActivityIntent);
            }
        });
    }
}