package com.one.javanotesapp;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Update_Notes extends AppCompatActivity {

    EditText updateNotes, updateTitle;
    Button updatebtn;

    String notesTitle, notes, id;

    MyDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);

        updateTitle = findViewById(R.id.updateTitle);
        updateNotes = findViewById(R.id.updateNotes);
        updatebtn = findViewById(R.id.updatebtn);

        id = getIntent().getStringExtra("ID");
        notesTitle = getIntent().getStringExtra("Title");
        notes = getIntent().getStringExtra("Notes");

        updateTitle.setText(notesTitle);
        updateNotes.setText(notes);

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    notesTitle = updateTitle.getText().toString();
                    notes = updateNotes.getText().toString();
                    if(notesTitle.isEmpty()) {
                        Toast.makeText(Update_Notes.this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                    }else{
                        if (notes.isEmpty()) {
                            Toast.makeText(Update_Notes.this, "notes cannot be empty", Toast.LENGTH_SHORT).show();
                        } else {
                            db = new MyDBHelper(Update_Notes.this, Update_Notes.this);
                            db.update(id, notesTitle, notes);
                        }
                    }
            }
        });
    }
}