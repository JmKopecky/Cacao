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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import dev.prognitio.cacao.R;
import dev.prognitio.cacao.fragments.change_email;
import dev.prognitio.cacao.fragments.change_feed;
import dev.prognitio.cacao.fragments.change_name;
import dev.prognitio.cacao.fragments.change_username;
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
                SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.userinfo_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                EditText NameText = findViewById(R.id.name_text);
                String name = NameText.getText().toString();
                editor.putString("usersupplied_name", name);
                editor.apply();
                NameText.setText("" + sharedPref.getString("usersupplied_name", "no username"));
            }
        });

        Button button_usermane = findViewById(R.id.username_button);
        button_usermane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.userinfo_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                EditText NameText = findViewById(R.id.username_text);
                String name = NameText.getText().toString();
                editor.putString("usersupplied_username", name);
                editor.apply();
                NameText.setText("" + sharedPref.getString("usersupplied_username", "no username"));
            }
        });

        Button button_email = findViewById(R.id.email_button);
        button_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.userinfo_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                EditText NameText = findViewById(R.id.email_text);
                String name = NameText.getText().toString();
                editor.putString("usersupplied_email", name);
                editor.apply();
                NameText.setText("" + sharedPref.getString("usersupplied_email", "no username"));
            }
        });

        Button button_feed = findViewById(R.id.change_probability);
        button_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.userinfo_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                EditText NameText = findViewById(R.id.);
                String name = NameText.getText().toString();
                float asFloat;
                try {
                    asFloat = Float.parseFloat(name);
                } catch (NumberFormatException e) {
                    asFloat = 0.5f;
                }
                editor.putFloat("usersupplied_feed", asFloat);
                editor.apply();
                NameText.setText("" + sharedPref.getFloat("usersupplied_feed", 0.5f));
            }
        });
    }
}