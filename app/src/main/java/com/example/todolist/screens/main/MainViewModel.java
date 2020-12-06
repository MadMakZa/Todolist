package com.example.todolist.screens.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.App;
import com.example.todolist.model.Note;

import java.util.List;

/**
 * Класс для отображения списка на главном экране
 */
public class MainViewModel extends ViewModel {

    private LiveData<List<Note>> noteLiveData = App.getInstance().getNoteDao().getAllLiveData();

    public LiveData<List<Note>> getNoteLiveData() {
        return noteLiveData;
    }
}
