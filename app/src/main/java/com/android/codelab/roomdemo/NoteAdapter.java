package com.android.codelab.roomdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> noteList;
    private Context context;
    private OnDeleteClickListener onDeleteClickListener;


    public NoteAdapter(Context context, OnDeleteClickListener onDeleteClickListener) {
        this.context = context;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NoteViewHolder holder, int position) {
        if (noteList != null) {
            Note note = noteList.get(position);
            holder.setData(note.getNote(), position);
            holder.setListener();

        } else {
            holder.tvNote.setText("No note available");

        }
    }

    @Override
    public int getItemCount() {
        if (noteList != null) {
            return noteList.size();
        } else
            return 0;
    }

    public void setData(List<Note> notes) {
        noteList = notes;
        notifyDataSetChanged();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNote;
        private int mPosition;
        ImageView ivDelete, ivEdit;


        public NoteViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvNote = itemView.findViewById(R.id.tvNote);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            ivEdit = itemView.findViewById(R.id.ivEdit);


        }

        public void setData(String note, int position) {
            tvNote.setText(note);
            mPosition = position;

        }

        public void setListener() {
            ivEdit.setOnClickListener(v -> {
                Intent intent = new Intent(context, EditNoteActivity.class);
                intent.putExtra("note_id", noteList.get(mPosition).getId());
                // pass id to activity

                // we want to go back to main Activity after edit task is complete
                ((Activity) context).startActivityForResult(intent, MainActivity.UPDATE_NOTE_REQUEST_CODE);


            });
            ivDelete.setOnClickListener(v -> {

                if (onDeleteClickListener != null) {
                    onDeleteClickListener.OnDeleteClickListener(noteList.get(getAdapterPosition()));
                }
            });
        }
    }
}
