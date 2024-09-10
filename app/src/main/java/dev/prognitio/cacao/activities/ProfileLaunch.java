package dev.prognitio.cacao.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dev.prognitio.cacao.R;
import dev.prognitio.cacao.log.LogType;
import dev.prognitio.cacao.log.Logger;

public class ProfileLaunch extends AppCompatActivity {

    Button proceedToPreferencesButton;
    EditText nameText;
    EditText usernameText;
    EditText emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_launch);

        Context context = getApplicationContext();

        proceedToPreferencesButton = findViewById(R.id.finishSetupButton);
        nameText = findViewById(R.id.profilenamefield);
        usernameText = findViewById(R.id.profileusernamefield);
        emailText = findViewById(R.id.profileemailfield);

        proceedToPreferencesButton.setOnClickListener(view -> {
            Logger.log("ProceedToPreferences Button Activated", LogType.DEBUG, null);

            //retrieve and store the values in the three text fields.

            String nameSubmission = nameText.getText().toString();
            String usernameSubmission = usernameText.getText().toString();
            String emailSubmission = emailText.getText().toString();

            if (!nameSubmission.isEmpty() && !usernameSubmission.isEmpty() && !emailSubmission.isEmpty()) {
                //store data
                SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.userinfo_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString("usersupplied_name", nameSubmission);
                editor.putString("usersupplied_username", usernameSubmission);
                editor.putString("usersupplied_email", emailSubmission);

                editor.apply();

                //switch activity
                Intent switchActivityIntent = new Intent(context, PreferencesSelection.class);
                startActivity(switchActivityIntent);
            } else {
                Logger.log("Failed to save user info in ProfileLaunch.java\nnameSubmission: " + nameSubmission
                        + "\nusernameSubmission: " + usernameSubmission
                        + "\nemailSubmission: " + emailSubmission, LogType.DEBUG, null);
            }
        });
    }

    public static class FeedActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_feed);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
    }
}