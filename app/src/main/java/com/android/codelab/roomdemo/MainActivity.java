package com.android.codelab.roomdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.codelab.roomdemo.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements OnDeleteClickListener {

    private static final String TAG = "MainActivity";
    private static final int NEW_NOTE_REQUEST_CODE = 1;

    public static final int UPDATE_NOTE_REQUEST_CODE = 3;

    private ActivityMainBinding binding;

    private NoteViewModel noteViewModel;
    private NoteAdapter noteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        noteAdapter = new NoteAdapter(this, this);
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        // Anonymous new View.OnClickListener() can be replaced with lambda

        binding.fab.setOnClickListener(v -> {


                    Intent intent = new Intent(this, NewNoteActivity.class);

                    // here we are using startActivityForResult instead of startActivity
                    // because we want to again come back to previous activity after adding the note

                    startActivityForResult(intent, NEW_NOTE_REQUEST_CODE);

                }

        );

        // noteViewModel instance
        noteViewModel = new ViewModelProvider(MainActivity.this).get(NoteViewModel.class);


        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setData(notes);

                if (noteAdapter.getItemCount() == 0) {
                    binding.tvNoData.setVisibility(View.VISIBLE);
                } else {
                    binding.tvNoData.setVisibility(View.GONE);
                }

            }
        });


    }

    // call back function for startActivityForResult


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_NOTE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            // code to insert data

            // for id
            final String note_id = UUID.randomUUID().toString();
            Note note = new Note(note_id, data.getStringExtra(NewNoteActivity.NOTE_ADDED));
            noteViewModel.insert(note);

            Snackbar.make(binding.getRoot(), "Note Saved !!", Snackbar.LENGTH_SHORT).show();
        } else if (requestCode == UPDATE_NOTE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            //code to update note
            Note note = new Note(data.getStringExtra(EditNoteActivity.NOTE_ID), data.getStringExtra(EditNoteActivity.UPDATED_NOTE));
            noteViewModel.updateNote(note);

            Snackbar.make(binding.getRoot(), "Note updated !!", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(binding.getRoot(), "Note Not Saved !!", Snackbar.LENGTH_SHORT).show();

        }
    }

    @Override
    public void OnDeleteClickListener(Note myNote) {
        // perform delete operation
        noteViewModel.deleteNote(myNote);

    }
}