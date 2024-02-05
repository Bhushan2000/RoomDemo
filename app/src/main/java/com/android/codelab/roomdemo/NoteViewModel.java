package com.android.codelab.roomdemo;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    // in previous viewModelDemo we have seen viewModel class extends viewModel

    // Here we are using "AndroidViewModel" so that we get application context



    private String TAG = this.getClass().getSimpleName();


    //instance of DAO and database
    private NoteRoomDatabase noteRoomDatabase;
    private NoteDAO noteDAO;

    // get the list of notes using liveData
    LiveData<List<Note>> mAllNotes;




    // constructor in which application is passed
    public NoteViewModel(@NonNull @NotNull Application application) {
        super(application);
        Log.d(TAG, "NoteViewModel: called");

        // we initialize the DB and DAO
        noteRoomDatabase = NoteRoomDatabase.getInstance(application);
        // to initiated DB we need context
        // that is getting from AndroidViewModel that's the reason we extend our viewModel with AndroidViewModel and not ViewModel

        noteDAO = noteRoomDatabase.noteDAO();
        mAllNotes = noteDAO.getAllNotes();
        // scenes we using liveData to get all notes this automatically perform in background thread.
        // so no need to ude async Task

    }

    // wrapper method for insert
    public void insert(Note note) {
        // As we know to perform this operation on non UI thread
        // We will do this using asyncTask

        new InsertTask(noteDAO).execute(note);

    }



//    wrapper function for get all
    LiveData<List<Note>> getAllNotes(){
        // scenes we using liveData to get all notes this automatically perform in background thread.
        // so no need to ude async Task to get notes

        return mAllNotes;
    }


    public void deleteNote(Note note) {
        new DeleteAsyncTask(noteDAO).execute(note);

    }

    public void updateNote(Note note) {
        new UpdateAsyncTask(noteDAO).execute(note);

    }


    // this perform insert operation in background thread (asynchronously)
    private class InsertTask extends OperationsAsyncTask {


        public InsertTask(NoteDAO mNoteDao) {
            super(mNoteDao);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.insert(notes[0]);
            return null;

        }
    }



    // code for update note

    public class UpdateAsyncTask extends OperationsAsyncTask {


        public UpdateAsyncTask(NoteDAO mNoteDao) {
            super(mNoteDao);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.update(notes[0]);
            return null;
        }
    }



    // code for delete note

    public class DeleteAsyncTask extends OperationsAsyncTask {


        public DeleteAsyncTask(NoteDAO mNoteDao) {
            super(mNoteDao);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.delete(notes[0]);
            return null;
        }
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "onCleared: ViewModel cleared");
    }
}
