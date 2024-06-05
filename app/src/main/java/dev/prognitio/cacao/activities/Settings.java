package dev.prognitio.cacao.activities;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import dev.prognitio.cacao.R;
import dev.prognitio.cacao.fragments.change_name;
import dev.prognitio.cacao.log.LogType;
import dev.prognitio.cacao.log.Logger;

public class Settings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(getString(R.string.userinfo_key), Context.MODE_PRIVATE);

        String name = sharedPreferences.getString("usersupplied_name", "");
        TextView text_name = findViewById(R.id.name_text);
        text_name.setText(name);

        String username = sharedPreferences.getString("usersupplied_username", "");
        TextView text_username = findViewById(R.id.username_text);
        text_username.setText(username);

        String email = sharedPreferences.getString("usersupplied_email", "");
        TextView text_email = findViewById(R.id.email_text);
        text_email.setText(email);


        Button button_name = findViewById(R.id.name_button);
        button_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView4, change_name.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });
    }
}