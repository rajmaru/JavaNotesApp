package com.one.javanotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_Notes extends AppCompatActivity {

    EditText addTitle;
    EditText addNotes;
    Button savebtn;
    private String notesTitle;
    private String notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        addTitle = findViewById(R.id.addTitle);
        addNotes = findViewById(R.id.addNotes);
        savebtn = findViewById(R.id.savebtn);

        MyDBHelper db = new MyDBHelper (this);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    notesTitle = addTitle.getText().toString();
                    notes = addNotes.getText().toString();
                    if(notesTitle.isEmpty()) {
                        Toast.makeText(Add_Notes.this, "Title cannot be empty.", Toast.LENGTH_SHORT).show();
                    }else{
                        if(notes.isEmpty()){
                        Toast.makeText(Add_Notes.this, "Notes cannot be empty.", Toast.LENGTH_SHORT).show();
                        }else {
                            db.addNotes(notesTitle, notes);
                        }
                    }
            }
        });

    }
}