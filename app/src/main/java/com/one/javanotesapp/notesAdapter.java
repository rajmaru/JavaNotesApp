package com.one.javanotesapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class notesAdapter extends RecyclerView.Adapter<notesAdapter.notesViewHolder>{

    Context context;
    ArrayList<String> notesTitles;
    ArrayList<String> notesID;
    ArrayList<String> notes;
    Activity activity;

    public notesAdapter(Activity activity, Context context, ArrayList<String> notesID, ArrayList<String> notesTitles, ArrayList<String> notes) {
        this.context = context;
        this.notesID = notesID;
        this.notesTitles = notesTitles;
        this.notes = notes;
        this.activity = activity;
    }

    @NonNull
    @Override
    public notesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes_row, parent,false);
        return new notesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull notesViewHolder holder, int position) {
        holder.notesTitle.setText(notesTitles.get(holder.getAdapterPosition()));
        holder.notes.setText(notes.get(holder.getAdapterPosition()));
        holder.notesList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Update_Notes.class);
                intent.putExtra("ID", String.valueOf(notesID.get(holder.getAdapterPosition())));
                intent.putExtra("Title", String.valueOf(notesTitles.get(holder.getAdapterPosition())));
                intent.putExtra("Notes", String.valueOf(notes.get(holder.getAdapterPosition())));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesTitles.size();
    }

    public class notesViewHolder extends RecyclerView.ViewHolder{

        TextView notesTitle;
        TextView notes;
        ConstraintLayout notesList;

        public notesViewHolder(@NonNull View itemView) {
            super(itemView);

            notesTitle = itemView.findViewById(R.id.notesTitle);
            notes = itemView.findViewById(R.id.notes);
            notesList = itemView.findViewById(R.id.notesList);
        }
    }
}
