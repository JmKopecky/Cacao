package dev.prognitio.cacao.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

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
        setContentView(R.layout.activity_note_management);
        Context context = getApplicationContext();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String noteTitle = extras.getString("note");
            System.out.println("NoteTitle: " + noteTitle);
            nameField = findViewById(R.id.editnotename);
            contentField = findViewById(R.id.editnotecontent);
            weightField = findViewById(R.id.editnoteweight);
            SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.usernotes_key), Context.MODE_PRIVATE);
            nameField.setText(noteTitle.split("_")[1]);
            System.out.println(Notes.fromString(sharedPref.getString(noteTitle, "")));
            contentField.setText(Notes.fromString(sharedPref.getString(noteTitle, "")).getContent());
            weightField.setText("" + Notes.fromString(sharedPref.getString(noteTitle, "")).getWeight());

        } else {
            nameField = findViewById(R.id.editnotename);
            contentField = findViewById(R.id.editnotecontent);
            weightField = findViewById(R.id.editnoteweight);
        }



        returnToNoteScreenButton = findViewById(R.id.returntonotedisplaybutton);
        attemptEditButton = findViewById(R.id.finalizenotebutton);


        attemptEditButton.setOnClickListener(view -> {
            String name = nameField.getText().toString();
            String content = contentField.getText().toString();
            String weight = weightField.getText().toString();

            try {
                int weightAsInt = Integer.parseInt(weight);
                if (weightAsInt < 1 || weightAsInt > 10) {
                    weightField.setTextColor(getResources().getColor(R.color.red));
                    return;
                }
            } catch (Exception e) {
                weightField.setTextColor(getResources().getColor(R.color.red));
                return;
            }


            Bundle data = getIntent().getExtras();
            String previousNoteTitle = "";
            if (data != null) {
                previousNoteTitle = data.getString("note");
            }

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

                if (!previousNoteTitle.isEmpty() && !previousNoteTitle.equals(name)) {
                    editor.remove("note_" + previousNoteTitle);
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