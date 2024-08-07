package dev.prognitio.cacao.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dev.prognitio.cacao.Notes;
import dev.prognitio.cacao.R;

public class EditNoteActivity extends AppCompatActivity {


    ImageButton returnToNoteScreenButton;
    Button attemptEditButton;

    EditText nameField;
    EditText contentField;
    EditText weightField;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Context context = getApplicationContext();

        nameField = findViewById(R.id.editnotename);
        contentField = findViewById(R.id.editnotecontent);
        weightField = findViewById(R.id.editnoteweight);

        returnToNoteScreenButton = findViewById(R.id.returntonotedisplaybutton);
        attemptEditButton = findViewById(R.id.finalizenotebutton);


        attemptEditButton.setOnClickListener(view -> {
            String name = nameField.getText().toString();
            String content = contentField.getText().toString();
            String weight = weightField.getText().toString();

            try {
                int weightAsInt = Integer.parseInt(weight);
                Notes note = new Notes(name, content, weightAsInt);
                String noteAsString = note.toString();

                //store data
                SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.usernotes_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                if (sharedPref.contains("note_" + note.getTitle())) {
                    //already existing
                    editor.putString("note_" + note.getTitle(), noteAsString);
                } else {
                    //new note
                    if (!sharedPref.contains("noteregistry")) {
                        //first note added
                        editor.putString("noteregistry", note.getTitle());
                        editor.putString("note_" + note.getTitle(), noteAsString);
                    } else {
                        editor.putString("noteregistry",
                                sharedPref.getString("noteregistry", "") + "," + note.getTitle());
                        editor.putString("note_" + note.getTitle(), noteAsString);
                    }
                }
                editor.apply();

                Intent switchActivityIntent = new Intent(context, NotesActivity.class);
                startActivity(switchActivityIntent);
            } catch (Exception e) {
                System.out.println("Failed to edit note.");
                e.printStackTrace();
            }
        });


        returnToNoteScreenButton.setOnClickListener(view -> {
            Intent switchActivityIntent = new Intent(context, NotesActivity.class);
            startActivity(switchActivityIntent);
        });
    }
}