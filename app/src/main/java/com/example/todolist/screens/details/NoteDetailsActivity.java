package com.example.todolist.screens.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.App;
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
        //достаем едит текст по айди
        editText = findViewById(R.id.text);

        if (getIntent().hasExtra(EXTRA_NOTE)){
            note = getIntent().getParcelableExtra(EXTRA_NOTE);
            editText.setText(note.text);
        } else {
            //если заметки нет создаем новую
            note = new Note();
        }

    }
    //кнопочка сохранить
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu); //куда извлечь меню
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_save:
                if(editText.getText().length() > 0){
                    note.text = editText.getText().toString();
                    note.done = false;
                    note.timestamp = System.currentTimeMillis();
                    if(getIntent().hasExtra(EXTRA_NOTE)){
                        App.getInstance().getNoteDao().update(note); //обновить заметку в бд
                    }else{
                        App.getInstance().getNoteDao().insert(note); //вставить заметку
                    }
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }
}
