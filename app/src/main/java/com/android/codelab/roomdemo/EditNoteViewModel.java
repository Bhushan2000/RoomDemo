package com.android.codelab.roomdemo;

import android.app.Application;
import android.app.AsyncNotedAppOp;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

public class EditNoteViewModel extends AndroidViewModel {
    private String TAG = this.getClass().getSimpleName();


    private NoteRoomDatabase noteRoomDatabase;
    private NoteDAO noteDAO;


    public EditNoteViewModel(@NonNull @NotNull Application application) {
        super(application);
        Log.d(TAG, "EditNoteViewModel: called");
        noteRoomDatabase = NoteRoomDatabase.getInstance(application);
        noteDAO = noteRoomDatabase.noteDAO();

    }

    public LiveData<Note> getNote(String noteId) {
        return noteDAO.getNote(noteId);

    }



}
