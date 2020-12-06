package com.example.todolist.screens.main;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.todolist.App;
import com.example.todolist.R;
import com.example.todolist.model.Note;
import com.example.todolist.screens.details.NoteDetailsActivity;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.NoteViewHolder> {

    //для работы анимаций
    private SortedList<Note> sortedList;

    public Adapter() {

        sortedList = new SortedList<>(Note.class, new SortedList.Callback<Note>() {
            //сравнивает между собой два объекта
            @Override
            public int compare(Note o1, Note o2) {
                if(!o2.done && o1.done){
                    return 1;
                }
                if(o2.done && !o1.done){
                    return -1;
                }
                return (int) (o2.timestamp - o1.timestamp);
            }
            //вызывается при изменении одного элемента в списке
            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }
            //возвращает тру если 2 элемента равны
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean areContentsTheSame(Note oldItem, Note newItem) {
                return oldItem.equals(newItem);
            }
            //если поменяли текст в заметке
            @Override
            public boolean areItemsTheSame(Note item1, Note item2) {
                return item1.uid == item2.uid;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }
    //сортед лист сравнит содержимое с новым содержимым
    public void setItems(List<Note> notes){
        sortedList.replaceAll(notes);
    }

    //класс котроый хранит ссылки на вьюхи
    static class NoteViewHolder extends RecyclerView.ViewHolder{

        //кнопки
        TextView noteText;
        CheckBox completed;
        View delete;

        Note note;

        boolean silentUodate;

        public NoteViewHolder(@NonNull final View itemView){
            super(itemView);

            noteText = itemView.findViewById(R.id.note_text);
            completed = itemView.findViewById(R.id.complited);
            delete = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NoteDetailsActivity.start((Activity)itemView.getContext(), note);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.getInstance().getNoteDao().delete(note);
                }
            });

            completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean checked) {
                    if(!silentUodate){
                        note.done = checked;
                        App.getInstance().getNoteDao().update(note);
                    }
                    //зачеркивание строки
                    updateStrokeOut();
                }
            });
        }

        //отображает значение полей заметки
        public void bind(Note note){
            //запоминаем заметку с которой работаем
            this.note = note;
            //записываем в поле для текста текст заметки
            noteText.setText(note.text);
            updateStrokeOut();

            silentUodate = true;
            completed.setChecked(note.done);
            silentUodate = false;


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
