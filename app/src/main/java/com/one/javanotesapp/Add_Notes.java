package com.one.javanotesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_Notes extends AppCompatActivity {

    EditText addTitle;
    EditText addNotes;
    MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        addTitle = findViewById(R.id.addTitle);
        addNotes = findViewById(R.id.addNotes);

        dbHelper = new MyDBHelper (this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.savebtn) {
            String notesTitle = addTitle.getText().toString();
            String notes = addNotes.getText().toString();
                    if(notesTitle.isEmpty()) {
                        Toast.makeText(Add_Notes.this, "Title cannot be empty.", Toast.LENGTH_SHORT).show();
                    }else{
                        if(notes.isEmpty()){
                        Toast.makeText(Add_Notes.this, "Notes cannot be empty.", Toast.LENGTH_SHORT).show();
                        }else {
                            dbHelper.addNotes(notesTitle, notes);
                            MainActivity.canRefresh = true;
                            finish();
                        }
                    }
        }

        return super.onOptionsItemSelected(item);
    }
}