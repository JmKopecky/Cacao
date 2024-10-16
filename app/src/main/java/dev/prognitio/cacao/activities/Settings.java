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
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.name_fragment, change_name.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name_fragment")
                        .commit();
            }
        });

        Button button_username = findViewById(R.id.username_button);
        button_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.username_fragment, change_username.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("username_fragment")
                        .commit();
            }
        });

        Button button_email = findViewById(R.id.email_button);
        button_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                //todo finish making fragments work.
                fragmentManager.beginTransaction()
                        .replace(R.id.email_fragment, change_email.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("email_fragment")
                        .commit();
            }
        });

        Button button_feed = findViewById(R.id.change_probability);
        button_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.feed_fragment, change_feed.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("feed_fragment")
                        .commit();
            }
        });

//        Button button_color = findViewById(R.id.app_color_button);
//        button_color.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fragmentManager = getSupportFragmentManager();
//
//                fragmentManager.beginTransaction()
//                        .replace(R.id.color_fragment, change_name.class, null)
//                        .setReorderingAllowed(true)
//                        .addToBackStack("email")
//                        .commit();
//            }
//        });
    }
}