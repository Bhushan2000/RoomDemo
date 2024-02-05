package com.android.codelab.roomdemo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
// if you don't give table name then the Room automatically takes the class name as Table name
public class Note {

    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "note")
    private String note;

    /// this constructor and getter setter methods are used by room for initiated our object
    public Note(String id, String note) {
        this.id = id;
        this.note = note;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
