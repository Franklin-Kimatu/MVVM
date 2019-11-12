package com.moringa.architecturemvvm;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

// this class create the first instance of the database

@Database(entities = {Note.class},version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    //this is creating the single instance of the class
    //this is a singleton class
    private static NoteDatabase instance;

    //using this method to access the dao file
    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if (instance ==null){//we instantiate this database when there is no instance
            instance = Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()//if you change the version of the database,we have to tell room on how to migrate to the new versoion
                    .addCallback(roomCallBack)
            .build();
        }
        return instance;
    }

    public static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;

        private PopulateDbAsyncTask(NoteDatabase db){
            noteDao =db.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1","Description 1",1));
            noteDao.insert(new Note("Title 2","Description 2",2));
            noteDao.insert(new Note("Title 3","Description 3",3));
            return null;
        }
    }
}
