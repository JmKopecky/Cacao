package dev.prognitio.cacao.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.time.LocalDate;
import java.util.Date;

import dev.prognitio.cacao.Assignment;
import dev.prognitio.cacao.R;

public class AddAssignmentActivity extends AppCompatActivity {

    ImageButton returnToCourseScreenButton;
    Button attemptCreateAssignmentButton;

    EditText titleField;
    EditText courseField;
    EditText detailField;
    EditText dateField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
        Context context = getApplicationContext();


        titleField = findViewById(R.id.editassignmenttitle);
        courseField = findViewById(R.id.editassignmentcourse);
        detailField = findViewById(R.id.editassignmentdetails);
        dateField = findViewById(R.id.editassignmentdate);


        attemptCreateAssignmentButton = findViewById(R.id.createassignmentbutton);
        attemptCreateAssignmentButton.setOnClickListener(view -> {
            String title = titleField.getText().toString();
            String course = courseField.getText().toString();
            String detail = detailField.getText().toString();
            String date = dateField.getText().toString();

            LocalDate asTrueDate;
            try {
                System.out.println(date);
                asTrueDate = LocalDate.of(Integer.parseInt(date.split("/")[0]), Integer.parseInt(date.split("/")[1]), Integer.parseInt(date.split("/")[2]));
            } catch (Exception e) {
                //do not add assignment, formatting failed.
                System.out.println("Formatting assignment date failed");
                return;
            }
            Assignment assignment = new Assignment(course, date, title, detail);

            String assignmentAsString = assignment.toString();

            System.out.println(assignment.getDueDate());
            System.out.println(assignmentAsString);

            SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.userassignments_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            int assignmentMaxIndex = sharedPref.getInt("assignment_maxindex", 0);
            assignmentMaxIndex++;
            editor.putString("assignment_" + assignmentMaxIndex, assignmentAsString);
            editor.putInt("assignment_maxindex", assignmentMaxIndex);

            editor.apply();

            Intent switchActivityIntent = new Intent(context, CourseDisplayActivity.class);
            startActivity(switchActivityIntent);
        });


        returnToCourseScreenButton = findViewById(R.id.returntocoursedisplaybutton);
        returnToCourseScreenButton.setOnClickListener(view -> {
            Intent switchActivityIntent = new Intent(context, CourseDisplayActivity.class);
            startActivity(switchActivityIntent);
        });
    }
}