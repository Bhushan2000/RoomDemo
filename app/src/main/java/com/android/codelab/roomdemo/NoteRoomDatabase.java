package com.android.codelab.roomdemo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1,exportSchema = false)
// The database class need to be abstract
// it list all entities in the project
// add list of database access object that we will use
// create instance of database
// Now here there is one thing that need to be take care of
// that we have single instance of database to ensure that our database class is singleton
// acquire instance of Database using Room.databaseBuilder()

public abstract class NoteRoomDatabase extends RoomDatabase {
    abstract NoteDAO noteDAO();

    public static volatile NoteRoomDatabase noteDatabaseInstance;

    // volatile means that the variable changes at runtime and that the compiler should not cache its value for any reason.
    // This is only really a problem when sharing the variable amongst threads, you don't want a thread working with stale data,
    // so the compiler should never cache the value of a volatile variable reference.


    // creating singleton object of the database
    static NoteRoomDatabase getInstance(final Context context) {
        if (noteDatabaseInstance == null) {
            synchronized (NoteRoomDatabase.class) {
                if (noteDatabaseInstance == null) {
                    noteDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            NoteRoomDatabase.class, "note_database.db").build();

                }
            }
        }
        return noteDatabaseInstance;
    }

}
