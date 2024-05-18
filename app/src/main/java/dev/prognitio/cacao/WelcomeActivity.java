package dev.prognitio.cacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                //Intent switchActivityIntent = new Intent(context, DataActivity.class);
                //startActivity(switchActivityIntent);
                Logger.log("WelcomeActivity Button Activated", LogType.DEBUG, null);
            }
        });
    }
}