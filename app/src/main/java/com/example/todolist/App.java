package com.example.todolist;

import android.app.Application;

import androidx.room.Room;

import com.example.todolist.data.AppDatabase;
import com.example.todolist.data.NoteDao;

public class App extends Application {

    private AppDatabase database;
    private NoteDao noteDao;


    //синглтон
    private static App instance;
    public static App getInstance(){
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        //при создании приложения создается базаданных , выносим создание бд в отдельный поток
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "app-db-name")
                .allowMainThreadQueries()
                .build();
        //создание каркаса и всех методов для работы с бд
        noteDao = database.noteDao();

    }
    //getters and setters
    public AppDatabase getDatabase() {
        return database;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    public NoteDao getNoteDao() {
        return noteDao;
    }

    public void setNoteDao(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

}
