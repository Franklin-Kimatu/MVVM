package com.moringa.architecturemvvm;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;

    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){
        NoteDatabase database =NoteDatabase.getInstance(application);
        noteDao =database.noteDao();
        allNotes =noteDao.getAllNotes();
    }

    public void insert (Note note){
        new InsertNoteAsyncTask(noteDao).execute(note);

    }
    public void update(Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }
    public void delete(Note note){
        new DelateNoteAsyncTask(noteDao).execute(note);
    }
    public void deleteAllNotes(){
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    //room does not allow database operations on the main thread so we create the Asynch method to cater for that

    public static class InsertNoteAsyncTask extends AsyncTask <Note,Void,Void>{
        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao){
            this.noteDao =noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }
    public static class UpdateNoteAsyncTask extends AsyncTask <Note,Void,Void>{
        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao){
            this.noteDao =noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }
    public static class DelateNoteAsyncTask extends AsyncTask <Note,Void,Void>{
        private NoteDao noteDao;

        private DelateNoteAsyncTask(NoteDao noteDao){
            this.noteDao =noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
    public static class DeleteAllNotesAsyncTask extends AsyncTask <Void,Void,Void>{
        private NoteDao noteDao;

        private DeleteAllNotesAsyncTask(NoteDao noteDao){
            this.noteDao =noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
