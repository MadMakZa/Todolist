package com.example.todolist.screens.main;

import android.graphics.Paint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.model.Note;

public class Adapter extends RecyclerView.Adapter<> {

    //класс котроый хранит ссылки на вьюхи
    static class NoteViewHolder extends RecyclerView.ViewHolder{

        //кнопки
        TextView noteText;
        CheckBox complited;
        View delete;

        Note note

        public NoteViewHolder(@NonNull View itemView){
            super(itemView);

            noteText = itemView.findViewById(R.id.note_text);
            complited = itemView.findViewById(R.id.complited);
            delete = itemView.findViewById(R.id.delete);
        }

        //отображает значение полей заметки
        public void bind(Note note){
            //запоминаем заметку с которой работаем
            this.note = note;
            //записываем в поле для текста текст заметки
            noteText.setText(note.text);
            updateStrokeOut();


        }
        //вычеркивание дела
        private void updateStrokeOut(){
            if (note.done){
                noteText.setPaintFlags(noteText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG); //установить флажок в квадратик
            } else {
                noteText.setPaintFlags(noteText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG); //убрать флажок (побитовый сдвиг)
            }
        }

    }
}
