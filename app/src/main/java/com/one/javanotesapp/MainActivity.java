package com.one.javanotesapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addNotes;
    ArrayList<String> notesTitle = new ArrayList<>();
    ArrayList<String> notes = new ArrayList<>();
    ArrayList<String> notesID = new ArrayList<>();
    MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.notesRecyclerview);
        addNotes = findViewById(R.id.addNotes);

        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Add_Notes.class);
                startActivity(intent);
            }
        });

        dbHelper = new MyDBHelper(MainActivity.this, this);

        storeDataInArray();

        notesAdapter adapter = new notesAdapter(MainActivity.this,this, notesID, notesTitle, notes);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if(requestCode == 1){
            recreate();
        }
    }

    private void storeDataInArray() {
        Cursor cursor = dbHelper.readAllData();

        if (cursor != null) {
            cursor.moveToFirst();
        }

        do {
            notesID.add(cursor.getString(0));
            notesTitle.add(cursor.getString(1));
            notes.add(cursor.getString(2));
        } while (cursor.moveToNext());

    }
}