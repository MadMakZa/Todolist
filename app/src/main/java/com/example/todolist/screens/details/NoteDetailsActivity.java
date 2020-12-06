package com.example.todolist.screens.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.R;
import com.example.todolist.model.Note;

/**
 * Редактирование заметки
 */
public class NoteDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_NOTE = "NoteDetailsActivity.EXTRA_NOTE";

    private Note note;
    private EditText editText; //для ввода текста
    //запуск нового активити через интент
    public static void start(Activity caller, Note note){
        Intent intent = new Intent(caller, NoteDetailsActivity.class);
        if (note != null){
            intent.putExtra(EXTRA_NOTE, note);
        }
        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //создает активити с редактированием заметки
        setContentView(R.layout.activity_note_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //кнопка назад
        getSupportActionBar().setHomeButtonEnabled(true);

        //задаем текст заголовка
        setTitle(getString(R.string.note_details_title));
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }
}
