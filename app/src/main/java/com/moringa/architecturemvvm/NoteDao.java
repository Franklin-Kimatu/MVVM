package com.moringa.architecturemvvm;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    // these are methods that the room sql provide for us
    // they are similar to sql statements
    @Insert
    void  insert(Note note);

    @Update
    void update( Note note);

    @Delete
    void delete(Note note);

    // delete all notes from a list or table is not defined so we use query to define such methods
    @Query("DELETE from note_table")
    void deleteAllNotes();


    //this can be made to be livedata so that we can observe the changes of the data
    @Query("SELECT * FROM  note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();
}
