package com.android.codelab.roomdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.android.codelab.roomdemo.databinding.ActivityEditNoteBinding;

public class EditNoteActivity extends AppCompatActivity {

    public static final String NOTE_ID = "note_id";
    public static final String UPDATED_NOTE = "note_text";
    private ActivityEditNoteBinding binding;
    private EditNoteViewModel editNoteViewModel;
    private LiveData<Note> note;
    private String noteId;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bundle = getIntent().getExtras();
        if (bundle!=null){

            noteId = bundle.getString("note_id");

        }



        binding.buttonUpdate.setOnClickListener(v -> {
            String updateNote = binding.etNoteEdit.getText().toString();

            Intent intent = new Intent();
            intent.putExtra(NOTE_ID,noteId);
            intent.putExtra(UPDATED_NOTE,updateNote);
            setResult(RESULT_OK,intent);
            binding.etNoteEdit.setText("");

            finish();



        });

        binding.buttonCancel.setOnClickListener(v -> {
            binding.etNoteEdit.setText("");
            finish();
        });

        editNoteViewModel = new ViewModelProvider(this).get(EditNoteViewModel.class);

        note = editNoteViewModel.getNote(noteId);

        note.observe(this, new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                binding.etNoteEdit.setText(note.getNote());
            }
        });


    }
}