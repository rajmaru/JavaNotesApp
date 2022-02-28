package com.one.javanotesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Update_Notes extends AppCompatActivity {

    EditText updateNotes, updateTitle;
    Button updatebtn;

    String notesTitle, notes, id;

    MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);

        dbHelper = new MyDBHelper(Update_Notes.this);

        updateTitle = findViewById(R.id.updateTitle);
        updateNotes = findViewById(R.id.updateNotes);

        id = getIntent().getStringExtra("ID");
        notesTitle = getIntent().getStringExtra("Title");
        notes = getIntent().getStringExtra("Notes");

        updateTitle.setText(notesTitle);
        updateNotes.setText(notes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            confirmDialog();
        }

        if (item.getItemId() == R.id.savebtn) {
            notesTitle = updateTitle.getText().toString();
            notes = updateNotes.getText().toString();
            if (notesTitle.isEmpty()) {
                Toast.makeText(Update_Notes.this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                if (notes.isEmpty()) {
                    Toast.makeText(Update_Notes.this, "notes cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    dbHelper.update(id, notesTitle, notes);
                    MainActivity.canRefresh = true;
                    finish();
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you really want to delete this notes?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Integer result = dbHelper.delete(id);
                if(result == -1){
                    Toast.makeText(Update_Notes.this, "Failed", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(Update_Notes.this, "Deleted Successfully.", Toast.LENGTH_LONG).show();
                    MainActivity.canRefresh = true;
                    finish();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();
    }
}