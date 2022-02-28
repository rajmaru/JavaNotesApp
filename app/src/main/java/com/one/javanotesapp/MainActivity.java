package com.one.javanotesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    RecyclerView recyclerView;
    FloatingActionButton addNotes;
    ArrayList<String> notesTitle = new ArrayList<>();
    ArrayList<String> notes = new ArrayList<>();
    ArrayList<String> notesID = new ArrayList<>();
    MyDBHelper dbHelper;
    ImageView error;
    TextView noDataFound;
    public static Boolean canRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: called");

        recyclerView = findViewById(R.id.notesRecyclerview);
        addNotes = findViewById(R.id.addNotes);
        error = findViewById(R.id.errorImage);
        noDataFound = findViewById(R.id.noDataFound);

        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Add_Notes.class);
                startActivity(intent);
            }
        });

        dbHelper = new MyDBHelper( this);

        storeDataInArray();

        notesAdapter adapter = new notesAdapter(MainActivity.this,this, notesID, notesTitle, notes);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    void storeDataInArray() {
        Cursor cursor = dbHelper.readAllData();
        error.setVisibility(View.GONE);
        noDataFound.setVisibility(View.GONE);
        if (cursor != null && cursor.moveToFirst()){

            do {
                notesID.add(cursor.getString(0));
                notesTitle.add(cursor.getString(1));
                notes.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }else{
            error.setVisibility(View.VISIBLE);
            noDataFound.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.deleteAll){
           confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(canRefresh){
            recreate();
            canRefresh = false;
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All Notes");
        builder.setMessage("Do you really want to delete all notes?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dbHelper.deleteAll();
                Toast.makeText(MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                recreate();
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