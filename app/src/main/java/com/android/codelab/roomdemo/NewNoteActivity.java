package com.android.codelab.roomdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.android.codelab.roomdemo.databinding.ActivityNewNoteBinding;

public class NewNoteActivity extends AppCompatActivity {

    public static final String NOTE_ADDED = "note_added";
    private ActivityNewNoteBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // lambda function
        binding.buttonNewNote.setOnClickListener(v -> {

                    Intent resultIntent = new Intent();

                    if (TextUtils.isEmpty(binding.etNewNote.getText())) {
                        setResult(RESULT_CANCELED, resultIntent);

                    }else{
                        String note = binding.etNewNote.getText().toString();
                        resultIntent.putExtra(NOTE_ADDED,note); // key // value
                        setResult(RESULT_OK,resultIntent);
                    }
                    finish();
                }
        );
    }
}